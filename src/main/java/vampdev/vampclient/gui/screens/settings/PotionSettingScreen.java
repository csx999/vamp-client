

package vampdev.vampclient.gui.screens.settings;

import vampdev.vampclient.gui.GuiTheme;
import vampdev.vampclient.gui.WindowScreen;
import vampdev.vampclient.gui.widgets.containers.WTable;
import vampdev.vampclient.gui.widgets.pressable.WButton;
import vampdev.vampclient.settings.PotionSetting;
import vampdev.vampclient.utils.misc.MyPotion;

public class PotionSettingScreen extends WindowScreen {
    private final PotionSetting setting;

    public PotionSettingScreen(GuiTheme theme, PotionSetting setting) {
        super(theme, "Select Potion");

        this.setting = setting;
    }

    @Override
    public void initWidgets() {
        WTable table = add(theme.table()).expandX().widget();

        for (MyPotion potion : MyPotion.values()) {
            table.add(theme.itemWithLabel(potion.potion, potion.potion.getName().getString()));

            WButton select = table.add(theme.button("Select")).widget();
            select.action = () -> {
                setting.set(potion);
                onClose();
            };

            table.row();
        }
    }
}
