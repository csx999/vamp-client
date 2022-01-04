

package vampdev.vampclient.gui.utils;

import vampdev.vampclient.gui.GuiTheme;
import vampdev.vampclient.gui.widgets.WWidget;
import vampdev.vampclient.settings.Settings;

public interface SettingsWidgetFactory {
    WWidget create(GuiTheme theme, Settings settings, String filter);
}
