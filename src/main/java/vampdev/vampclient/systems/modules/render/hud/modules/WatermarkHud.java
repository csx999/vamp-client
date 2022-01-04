

package vampdev.vampclient.systems.modules.render.hud.modules;

import vampdev.vampclient.systems.config.Config;
import vampdev.vampclient.systems.modules.render.hud.HUD;

public class WatermarkHud extends DoubleTextHudElement {
    public WatermarkHud(HUD hud) {
        super(hud, "watermark", "Displays a Vamp Client watermark.", "Vamp Client ");
    }

    @Override
    protected String getRight() {
        if (Config.get().devBuild.isEmpty()) {
            return Config.get().version.toString();
        }

        return Config.get().version + " " + Config.get().devBuild;
    }
}
