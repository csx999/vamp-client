

package vampdev.vampclient.mixin;

import vampdev.vampclient.VampClient;
import vampdev.vampclient.events.meteor.MouseButtonEvent;
import vampdev.vampclient.events.meteor.MouseScrollEvent;
import vampdev.vampclient.mixininterface.ICamera;
import vampdev.vampclient.systems.modules.Modules;
import vampdev.vampclient.systems.modules.render.FreeLook;
import vampdev.vampclient.systems.modules.render.Freecam;
import vampdev.vampclient.systems.modules.world.HighwayBuilder;
import vampdev.vampclient.utils.misc.input.Input;
import vampdev.vampclient.utils.misc.input.KeyAction;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

@Mixin(Mouse.class)
public class MouseMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
    private void onMouseButton(long window, int button, int action, int mods, CallbackInfo info) {
        Input.setButtonState(button, action != GLFW_RELEASE);

        if (VampClient.EVENT_BUS.post(MouseButtonEvent.get(button, KeyAction.get(action))).isCancelled()) info.cancel();
    }

    @Inject(method = "onMouseScroll", at = @At("HEAD"), cancellable = true)
    private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo info) {
        if (VampClient.EVENT_BUS.post(MouseScrollEvent.get(vertical)).isCancelled()) info.cancel();
    }

    @Redirect(method = "updateMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;changeLookDirection(DD)V"))
    private void updateMouseChangeLookDirection(ClientPlayerEntity player, double cursorDeltaX, double cursorDeltaY) {
        Freecam freecam = Modules.get().get(Freecam.class);
        FreeLook freeLook = Modules.get().get(FreeLook.class);

        if (freecam.isActive()) freecam.changeLookDirection(cursorDeltaX * 0.15, cursorDeltaY * 0.15);
        else if (Modules.get().isActive(HighwayBuilder.class)) {
            Camera camera = client.gameRenderer.getCamera();
            ((ICamera) camera).setRot(camera.getYaw() + cursorDeltaX * 0.15, camera.getPitch() + cursorDeltaY * 0.15);
        }
        else if (freeLook.cameraMode()) {
            freeLook.cameraYaw += cursorDeltaX / freeLook.sensitivity.get().floatValue();
            freeLook.cameraPitch += cursorDeltaY / freeLook.sensitivity.get().floatValue();

            if (Math.abs(freeLook.cameraPitch) > 90.0F) freeLook.cameraPitch = freeLook.cameraPitch > 0.0F ? 90.0F : -90.0F;
        }
        else player.changeLookDirection(cursorDeltaX, cursorDeltaY);
    }
}