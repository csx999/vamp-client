

package vampdev.vampclient.gui.widgets;

import vampdev.vampclient.utils.render.color.Color;

public abstract class WQuad extends WWidget {
    public Color color;

    public WQuad(Color color) {
        this.color = color;
    }

    @Override
    protected void onCalculateSize() {
        double s = theme.scale(32);

        width = s;
        height = s;
    }
}
