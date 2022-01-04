

package vampdev.vampclient.gui.themes.meteor.widgets;

import vampdev.vampclient.gui.themes.meteor.VampWidget;
import vampdev.vampclient.gui.widgets.WTopBar;
import vampdev.vampclient.utils.render.color.Color;

public class WVampTopBar extends WTopBar implements VampWidget {
    @Override
    protected Color getButtonColor(boolean pressed, boolean hovered) {
        return theme().backgroundColor.get(pressed, hovered);
    }

    @Override
    protected Color getNameColor() {
        return theme().textColor.get();
    }
}
