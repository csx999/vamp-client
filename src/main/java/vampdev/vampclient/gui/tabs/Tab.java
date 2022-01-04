

package vampdev.vampclient.gui.tabs;

import vampdev.vampclient.gui.GuiTheme;
import net.minecraft.client.gui.screen.Screen;
import vampdev.vampclient.VampClient;

public abstract class Tab {
    public final String name;

    public Tab(String name) {
        this.name = name;
    }

    public void openScreen(GuiTheme theme) {
        TabScreen screen = this.createScreen(theme);
        screen.addDirect(theme.topBar()).top().centerX();
        VampClient.mc.setScreen(screen);
    }

    public abstract TabScreen createScreen(GuiTheme theme);

    public abstract boolean isScreen(Screen screen);
}
