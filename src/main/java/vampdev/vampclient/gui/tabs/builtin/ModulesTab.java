

package vampdev.vampclient.gui.tabs.builtin;

import vampdev.vampclient.gui.GuiTheme;
import vampdev.vampclient.gui.GuiThemes;
import vampdev.vampclient.gui.tabs.Tab;
import vampdev.vampclient.gui.tabs.TabScreen;
import net.minecraft.client.gui.screen.Screen;

public class ModulesTab extends Tab {
    public ModulesTab() {
        super("Modules");
    }

    @Override
    public TabScreen createScreen(GuiTheme theme) {
        return theme.modulesScreen();
    }

    @Override
    public boolean isScreen(Screen screen) {
        return GuiThemes.get().isModulesScreen(screen);
    }
}
