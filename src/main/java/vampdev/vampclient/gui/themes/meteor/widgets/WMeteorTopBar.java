

package vampdev.vampclient.gui.themes.meteor.widgets;

import vampdev.vampclient.gui.themes.meteor.MeteorWidget;
import vampdev.vampclient.gui.widgets.WTopBar;
import vampdev.vampclient.utils.render.color.Color;

public class WMeteorTopBar extends WTopBar implements MeteorWidget {
    @Override
    protected Color getButtonColor(boolean pressed, boolean hovered) {
        return theme().backgroundColor.get(pressed, hovered);
    }

    @Override
    protected Color getNameColor() {
        return theme().textColor.get();
    }
}
