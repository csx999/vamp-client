

package vampdev.vampclient.systems.modules.render.hud.modules;

import vampdev.vampclient.mixin.MinecraftClientAccessor;
import vampdev.vampclient.systems.modules.render.hud.HUD;

public class FpsHud extends DoubleTextHudElement {
    public FpsHud(HUD hud) {
        super(hud, "fps", "Displays your FPS.", "FPS: ");
    }

    @Override
    protected String getRight() {
        return Integer.toString(MinecraftClientAccessor.getFps());
    }
}
