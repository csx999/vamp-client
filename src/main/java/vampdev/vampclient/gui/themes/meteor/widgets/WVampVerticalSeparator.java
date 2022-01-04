

package vampdev.vampclient.gui.themes.meteor.widgets;

import vampdev.vampclient.gui.renderer.GuiRenderer;
import vampdev.vampclient.gui.themes.meteor.VampGuiTheme;
import vampdev.vampclient.gui.themes.meteor.VampWidget;
import vampdev.vampclient.gui.widgets.WVerticalSeparator;
import vampdev.vampclient.utils.render.color.Color;

public class WVampVerticalSeparator extends WVerticalSeparator implements VampWidget {
    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        VampGuiTheme theme = theme();
        Color colorEdges = theme.separatorEdges.get();
        Color colorCenter = theme.separatorCenter.get();

        double s = theme.scale(1);
        double offsetX = Math.round(width / 2.0);

        renderer.quad(x + offsetX, y, s, height / 2, colorEdges, colorEdges, colorCenter, colorCenter);
        renderer.quad(x + offsetX, y + height / 2, s, height / 2, colorCenter, colorCenter, colorEdges, colorEdges);
    }
}
