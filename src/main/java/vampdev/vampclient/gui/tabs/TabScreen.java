

package vampdev.vampclient.gui.tabs;

import vampdev.vampclient.gui.GuiTheme;
import vampdev.vampclient.gui.WidgetScreen;
import vampdev.vampclient.gui.utils.Cell;
import vampdev.vampclient.gui.widgets.WWidget;

public abstract class TabScreen extends WidgetScreen {
    public final Tab tab;

    public TabScreen(GuiTheme theme, Tab tab) {
        super(theme, tab.name);

        this.tab = tab;
    }

    public <T extends WWidget> Cell<T> addDirect(T widget) {
        return super.add(widget);
    }
}
