

package vampdev.vampclient.systems.modules.render.hud.modules;

import vampdev.vampclient.systems.modules.render.hud.HUD;
import vampdev.vampclient.systems.modules.render.hud.HudRenderer;
import vampdev.vampclient.utils.render.color.Color;

public abstract class DoubleTextHudElement extends HudElement {
    protected Color rightColor;
    protected boolean visible = true;

    private String left, right;
    private double leftWidth;

    public DoubleTextHudElement(HUD hud, String name, String description, String left, boolean defaultActive) {
        super(hud, name, description, defaultActive);
        this.rightColor = hud.secondaryColor.get();
        this.left = left;
    }

    public DoubleTextHudElement(HUD hud, String name, String description, String left) {
        super(hud, name, description, true);
        this.rightColor = hud.secondaryColor.get();
        this.left = left;
    }

    @Override
    public void update(HudRenderer renderer) {
        right = getRight();
        leftWidth = renderer.textWidth(left);

        box.setSize(leftWidth + renderer.textWidth(right), renderer.textHeight());
    }

    @Override
    public void render(HudRenderer renderer) {
        if (!visible) return;

        double x = box.getX();
        double y = box.getY();

        renderer.text(left, x, y, hud.primaryColor.get());
        renderer.text(right, x + leftWidth, y, rightColor);
    }

    protected void setLeft(String left) {
        this.left = left;
        this.leftWidth = 0;
    }

    protected abstract String getRight();
}
