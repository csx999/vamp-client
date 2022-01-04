

package vampdev.vampclient.gui.themes.meteor.widgets;

import vampdev.vampclient.gui.renderer.GuiRenderer;
import vampdev.vampclient.gui.widgets.WQuad;
import vampdev.vampclient.utils.render.color.Color;

public class WVampQuad extends WQuad {
    public WVampQuad(Color color) {
        super(color);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        renderer.quad(x, y, width, height, color);
    }
}
