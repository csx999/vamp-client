

package vampdev.vampclient.gui.tabs;

import vampdev.vampclient.gui.GuiTheme;
import vampdev.vampclient.gui.utils.Cell;
import vampdev.vampclient.gui.widgets.WWidget;
import vampdev.vampclient.gui.widgets.containers.WWindow;

public abstract class WindowTabScreen extends TabScreen {
    protected final WWindow window;

    public WindowTabScreen(GuiTheme theme, Tab tab) {
        super(theme, tab);

        window = super.add(theme.window(tab.name)).center().widget();
    }

    @Override
    public <W extends WWidget> Cell<W> add(W widget) {
        return window.add(widget);
    }

    @Override
    public void clear() {
        window.clear();
    }
}
