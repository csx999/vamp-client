

package vampdev.vampclient.mixin;

import vampdev.vampclient.systems.config.Config;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Mixin(SplashTextResourceSupplier.class)
public class SplashTextResourceSupplierMixin {
    private boolean override = true;
    private final Random random = new Random();

    private final List<String> vampSplashes = getVampSplashes();

    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    private void onApply(CallbackInfoReturnable<String> cir) {
        if (Config.get() == null || !Config.get().titleScreenSplashes) return;

        if (override) cir.setReturnValue(vampSplashes.get(random.nextInt(vampSplashes.size())));
        override = !override;
    }

    private static List<String> getVampSplashes() {
        return Arrays.asList(
                "Vamp Client!",
                "Utility mod.",
                "Join our Discord! vampclient.com/discord"
        );
    }

}
