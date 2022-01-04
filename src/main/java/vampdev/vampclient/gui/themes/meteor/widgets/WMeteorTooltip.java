

package vampdev.vampclient.gui.themes.meteor.widgets;

import vampdev.vampclient.gui.renderer.GuiRenderer;
import vampdev.vampclient.gui.themes.meteor.MeteorWidget;
import vampdev.vampclient.gui.widgets.WTooltip;

public class WMeteorTooltip extends WTooltip implements MeteorWidget {
    public WMeteorTooltip(String text) {
        super(text);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        renderer.quad(this, theme().backgroundColor.get());
    }
}
