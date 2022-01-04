

package vampdev.vampclient.systems.modules.misc;

import vampdev.vampclient.systems.modules.Categories;
import vampdev.vampclient.systems.modules.Module;

public class AntiPacketKick extends Module {
    public AntiPacketKick() {
        super(Categories.Misc, "anti-packet-kick", "Attempts to prevent you from being disconnected by large packets.");
    }
}
