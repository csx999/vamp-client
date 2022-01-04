

package vampdev.vampclient.systems.modules.render.hud.modules;

import vampdev.vampclient.settings.ColorSetting;
import vampdev.vampclient.settings.Setting;
import vampdev.vampclient.settings.SettingGroup;
import vampdev.vampclient.systems.modules.Modules;
import vampdev.vampclient.systems.modules.misc.NameProtect;
import vampdev.vampclient.systems.modules.render.hud.HUD;
import vampdev.vampclient.utils.render.color.SettingColor;

public class WelcomeHud extends DoubleTextHudElement {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<SettingColor> color = sgGeneral.add(new ColorSetting.Builder()
        .name("color")
        .description("Color of welcome text.")
        .defaultValue(new SettingColor(120, 43, 153))
        .build()
    );

    public WelcomeHud(HUD hud) {
        super(hud, "welcome", "Displays a welcome message.", "Welcome to Vamp Client, ");
        rightColor = color.get();
    }

    @Override
    protected String getRight() {
        return Modules.get().get(NameProtect.class).getName(mc.getSession().getUsername()) + "!";
    }
}
