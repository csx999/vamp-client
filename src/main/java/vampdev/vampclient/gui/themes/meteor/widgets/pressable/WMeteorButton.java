

package vampdev.vampclient.gui.themes.meteor.widgets.pressable;

import vampdev.vampclient.gui.renderer.GuiRenderer;
import vampdev.vampclient.gui.renderer.packer.GuiTexture;
import vampdev.vampclient.gui.themes.meteor.MeteorGuiTheme;
import vampdev.vampclient.gui.themes.meteor.MeteorWidget;
import vampdev.vampclient.gui.widgets.pressable.WButton;

public class WMeteorButton extends WButton implements MeteorWidget {
    public WMeteorButton(String text, GuiTexture texture) {
        super(text, texture);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        MeteorGuiTheme theme = theme();
        double pad = pad();

        renderBackground(renderer, this, pressed, mouseOver);

        if (text != null) {
            renderer.text(text, x + width / 2 - textWidth / 2, y + pad, theme.textColor.get(), false);
        }
        else {
            double ts = theme.textHeight();
            renderer.quad(x + width / 2 - ts / 2, y + pad, ts, ts, texture, theme.textColor.get());
        }
    }
}
