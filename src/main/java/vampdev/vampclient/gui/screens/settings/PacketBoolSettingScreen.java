

package vampdev.vampclient.gui.screens.settings;

import vampdev.vampclient.gui.GuiTheme;
import vampdev.vampclient.gui.widgets.WWidget;
import vampdev.vampclient.settings.PacketListSetting;
import vampdev.vampclient.settings.Setting;
import vampdev.vampclient.utils.network.PacketUtils;
import net.minecraft.network.Packet;

import java.util.Set;
import java.util.function.Predicate;

public class PacketBoolSettingScreen extends LeftRightListSettingScreen<Class<? extends Packet<?>>> {
    public PacketBoolSettingScreen(GuiTheme theme, Setting<Set<Class<? extends Packet<?>>>> setting) {
        super(theme, "Select Packets", setting, setting.get(), PacketUtils.REGISTRY);
    }

    @Override
    protected boolean includeValue(Class<? extends Packet<?>> value) {
        Predicate<Class<? extends Packet<?>>> filter = ((PacketListSetting) setting).filter;

        if (filter == null) return true;
        return filter.test(value);
    }

    @Override
    protected WWidget getValueWidget(Class<? extends Packet<?>> value) {
        return theme.label(getValueName(value));
    }

    @Override
    protected String getValueName(Class<? extends Packet<?>> value) {
        return PacketUtils.getName(value);
    }
}
