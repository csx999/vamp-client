

package vampdev.vampclient.systems.modules.player;

import vampdev.vampclient.systems.modules.Categories;
import vampdev.vampclient.systems.modules.Module;

public class NoBreakDelay extends Module {
    public NoBreakDelay() {
        super(Categories.Player, "no-break-delay", "Completely removes the delay between breaking blocks.");
    }
}
