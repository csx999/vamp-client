

package vampdev.vampclient.systems.modules.render;

import vampdev.vampclient.systems.modules.Categories;
import vampdev.vampclient.systems.modules.Module;

public class UnfocusedCPU extends Module {
    public UnfocusedCPU() {
        super(Categories.Render, "unfocused-cpu", "Will not render anything when your Minecraft window is not focused.");
    }
}
