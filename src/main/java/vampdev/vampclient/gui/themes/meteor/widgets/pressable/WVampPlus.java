

package vampdev.vampclient.gui.themes.meteor.widgets.pressable;

import vampdev.vampclient.gui.renderer.GuiRenderer;
import vampdev.vampclient.gui.themes.meteor.VampGuiTheme;
import vampdev.vampclient.gui.themes.meteor.VampWidget;
import vampdev.vampclient.gui.widgets.pressable.WPlus;

public class WVampPlus extends WPlus implements VampWidget {
    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        VampGuiTheme theme = theme();
        double pad = pad();
        double s = theme.scale(3);

        renderBackground(renderer, this, pressed, mouseOver);
        renderer.quad(x + pad, y + height / 2 - s / 2, width - pad * 2, s, theme.plusColor.get());
        renderer.quad(x + width / 2 - s / 2, y + pad, s, height - pad * 2, theme.plusColor.get());
    }
}
