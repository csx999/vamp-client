

package vampdev.vampclient.systems.modules.render.hud.modules;

import vampdev.vampclient.systems.modules.render.hud.HUD;
import vampdev.vampclient.utils.world.TickRate;

public class TpsHud extends DoubleTextHudElement {
    public TpsHud(HUD hud) {
        super(hud, "tps", "Displays the server's TPS.", "TPS: ");
    }

    @Override
    protected String getRight() {
        return String.format("%.1f", TickRate.INSTANCE.getTickRate());
    }
}
