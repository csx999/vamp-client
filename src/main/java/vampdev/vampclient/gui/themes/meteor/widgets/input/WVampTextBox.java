

package vampdev.vampclient.gui.themes.meteor.widgets.input;

import vampdev.vampclient.gui.renderer.GuiRenderer;
import vampdev.vampclient.gui.themes.meteor.VampGuiTheme;
import vampdev.vampclient.gui.themes.meteor.VampWidget;
import vampdev.vampclient.gui.utils.CharFilter;
import vampdev.vampclient.gui.widgets.input.WTextBox;
import vampdev.vampclient.utils.Utils;

public class WVampTextBox extends WTextBox implements VampWidget {
    private boolean cursorVisible;
    private double cursorTimer;

    private double animProgress;

    public WVampTextBox(String text, CharFilter filter) {
        super(text, filter);
    }

    @Override
    protected void onCursorChanged() {
        cursorVisible = true;
        cursorTimer = 0;
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        if (cursorTimer >= 1) {
            cursorVisible = !cursorVisible;
            cursorTimer = 0;
        }
        else {
            cursorTimer += delta * 1.75;
        }

        renderBackground(renderer, this, false, false);

        VampGuiTheme theme = theme();
        double pad = pad();
        double overflowWidth = getOverflowWidthForRender();

        renderer.scissorStart(x + pad, y + pad, width - pad * 2, height - pad * 2);

        // Text content
        if (!text.isEmpty()) {
            renderer.text(text, x + pad - overflowWidth, y + pad, theme.textColor.get(), false);
        }

        // Text highlighting
        if (focused && (cursor != selectionStart || cursor != selectionEnd)) {
            double selStart = x + pad + getTextWidth(selectionStart) - overflowWidth;
            double selEnd = x + pad + getTextWidth(selectionEnd) - overflowWidth;

            renderer.quad(selStart, y + pad, selEnd - selStart, theme.textHeight(), theme.textHighlightColor.get());
        }

        // Cursor
        animProgress += delta * 10 * (focused && cursorVisible ? 1 : -1);
        animProgress = Utils.clamp(animProgress, 0, 1);

        if ((focused && cursorVisible) || animProgress > 0) {
            renderer.setAlpha(animProgress);
            renderer.quad(x + pad + getTextWidth(cursor) - overflowWidth, y + pad, theme.scale(1), theme.textHeight(), theme.textColor.get());
            renderer.setAlpha(1);
        }

        renderer.scissorEnd();
    }
}
