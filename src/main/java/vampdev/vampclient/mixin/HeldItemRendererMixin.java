

package vampdev.vampclient.mixin;

import com.google.common.base.MoreObjects;
import vampdev.vampclient.systems.modules.Modules;
import vampdev.vampclient.systems.modules.render.HandView;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vampdev.vampclient.VampClient;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {
    @ModifyVariable(method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V", at = @At(value = "STORE", ordinal = 0), index = 6)
    private float modifySwing(float swingProgress) {
        HandView module = Modules.get().get(HandView.class);
        Hand hand = MoreObjects.firstNonNull(VampClient.mc.player.preferredHand, Hand.MAIN_HAND);

        if (module.isActive()) {
            if (hand == Hand.OFF_HAND && !VampClient.mc.player.getOffHandStack().isEmpty()) {
                return swingProgress + module.offSwing.get().floatValue();
            }
            if (hand == Hand.MAIN_HAND && !VampClient.mc.player.getMainHandStack().isEmpty()) {
                return swingProgress + module.mainSwing.get().floatValue();
            }
        }

        return swingProgress;
    }

    @Inject(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"))
    private void sex(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (!item.isEmpty()) Modules.get().get(HandView.class).transform(matrices);
    }
}
