

package vampdev.vampclient.gui.widgets;

import vampdev.vampclient.gui.renderer.GuiRenderer;
import vampdev.vampclient.gui.tabs.Tab;
import vampdev.vampclient.gui.tabs.TabScreen;
import vampdev.vampclient.gui.tabs.Tabs;
import vampdev.vampclient.gui.widgets.containers.WHorizontalList;
import vampdev.vampclient.gui.widgets.pressable.WPressable;
import vampdev.vampclient.utils.render.color.Color;
import net.minecraft.client.gui.screen.Screen;
import vampdev.vampclient.VampClient;

import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;

public abstract class WTopBar extends WHorizontalList {
    protected abstract Color getButtonColor(boolean pressed, boolean hovered);

    protected abstract Color getNameColor();

    public WTopBar() {
        spacing = 0;
    }

    @Override
    public void init() {
        for (Tab tab : Tabs.get()) {
            add(new WTopBarButton(tab));
        }
    }

    protected class WTopBarButton extends WPressable {
        private final Tab tab;

        public WTopBarButton(Tab tab) {
            this.tab = tab;
        }

        @Override
        protected void onCalculateSize() {
            double pad = pad();

            width = pad + theme.textWidth(tab.name) + pad;
            height = pad + theme.textHeight() + pad;
        }

        @Override
        protected void onPressed(int button) {
            Screen screen = VampClient.mc.currentScreen;

            if (!(screen instanceof TabScreen) || ((TabScreen) screen).tab != tab) {
                double mouseX = VampClient.mc.mouse.getX();
                double mouseY = VampClient.mc.mouse.getY();

                tab.openScreen(theme);
                glfwSetCursorPos(VampClient.mc.getWindow().getHandle(), mouseX, mouseY);
            }
        }

        @Override
        protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
            double pad = pad();
            Color color = getButtonColor(pressed || (VampClient.mc.currentScreen instanceof TabScreen && ((TabScreen) VampClient.mc.currentScreen).tab == tab), mouseOver);

            renderer.quad(x, y, width, height, color);
            renderer.text(tab.name, x + pad, y + pad, getNameColor(), false);
        }
    }
}
