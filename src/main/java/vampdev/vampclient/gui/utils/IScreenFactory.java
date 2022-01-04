

package vampdev.vampclient.gui.utils;

import vampdev.vampclient.gui.GuiTheme;
import vampdev.vampclient.gui.WidgetScreen;

public interface IScreenFactory {
    WidgetScreen createScreen(GuiTheme theme);
}
