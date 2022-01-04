

package vampdev.vampclient.systems.modules.player;

import vampdev.vampclient.settings.BoolSetting;
import vampdev.vampclient.settings.Setting;
import vampdev.vampclient.settings.SettingGroup;
import vampdev.vampclient.systems.modules.Categories;
import vampdev.vampclient.systems.modules.Module;
import net.minecraft.item.PickaxeItem;

public class NoMiningTrace extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> onlyWhenHoldingPickaxe = sgGeneral.add(new BoolSetting.Builder()
            .name("only-when-holding-a-pickaxe")
            .description("Whether or not to work only when holding a pickaxe.")
            .defaultValue(true)
            .build()
    );

    public NoMiningTrace() {
        super(Categories.Player, "no-mining-trace", "Allows you to mine blocks through entities.");
    }

    public boolean canWork() {
        if (!isActive()) return false;

        if (onlyWhenHoldingPickaxe.get()) {
            return mc.player.getMainHandStack().getItem() instanceof PickaxeItem || mc.player.getOffHandStack().getItem() instanceof PickaxeItem;
        }

        return true;
    }
}
