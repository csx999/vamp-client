

package vampdev.vampclient.mixin;

import vampdev.vampclient.systems.config.Config;
import vampdev.vampclient.systems.modules.Category;
import vampdev.vampclient.systems.modules.Module;
import vampdev.vampclient.systems.modules.Modules;
import net.minecraft.util.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CrashReport.class)
public class CrashReportMixin {
    @Inject(method = "addStackTrace", at = @At("TAIL"))
    private void onAddStackTrace(StringBuilder sb, CallbackInfo info) {
        if (Modules.get() != null) {
            sb.append("\n\n");
            sb.append("-- Vamp Client --\n");
            sb.append("Version: ").append(Config.get().version).append("\n");

            if (!Config.get().devBuild.isEmpty()) {
                sb.append("Dev Build: ").append(Config.get().devBuild).append("\n");
            }

            for (Category category : Modules.loopCategories()) {
                List<Module> modules = Modules.get().getGroup(category);
                boolean active = false;
                for (Module module : modules) {
                    if (module != null && module.isActive()) {
                        active = true;
                        break;
                    }
                }

                if (active) {
                    sb.append("\n");
                    sb.append("[").append(category).append("]:").append("\n");

                    for (Module module : modules) {
                        if (module instanceof Module && module.isActive()) {
                            sb.append(module.title).append(" (").append(module.name).append(")\n");
                        }
                    }
                }
            }
        }
    }
}
