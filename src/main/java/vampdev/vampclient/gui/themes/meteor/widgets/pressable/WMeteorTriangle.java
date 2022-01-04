

package vampdev.vampclient.gui.themes.meteor.widgets.pressable;

import vampdev.vampclient.gui.renderer.GuiRenderer;
import vampdev.vampclient.gui.themes.meteor.MeteorWidget;
import vampdev.vampclient.gui.widgets.pressable.WTriangle;

public class WMeteorTriangle extends WTriangle implements MeteorWidget {
    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        renderer.rotatedQuad(x, y, width, height, rotation, GuiRenderer.TRIANGLE, theme().backgroundColor.get(pressed, mouseOver));
    }
}
