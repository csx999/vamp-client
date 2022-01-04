

package vampdev.vampclient.systems.modules.render.hud.modules;

import vampdev.vampclient.systems.modules.render.hud.HUD;
import vampdev.vampclient.utils.Utils;

public class ServerHud extends DoubleTextHudElement {
    public ServerHud(HUD hud) {
        super(hud, "server", "Displays the server you're currently in.", "Server: ");
    }

    @Override
    protected String getRight() {
        if (!Utils.canUpdate()) return "None";

        return Utils.getWorldName();
    }
}



