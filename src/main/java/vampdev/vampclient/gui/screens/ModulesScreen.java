

package vampdev.vampclient.gui.screens;

import vampdev.vampclient.gui.GuiTheme;
import vampdev.vampclient.gui.tabs.TabScreen;
import vampdev.vampclient.gui.tabs.Tabs;
import vampdev.vampclient.gui.utils.Cell;
import vampdev.vampclient.gui.widgets.containers.WContainer;
import vampdev.vampclient.gui.widgets.containers.WSection;
import vampdev.vampclient.gui.widgets.containers.WVerticalList;
import vampdev.vampclient.gui.widgets.containers.WWindow;
import vampdev.vampclient.gui.widgets.input.WTextBox;
import vampdev.vampclient.systems.modules.Category;
import vampdev.vampclient.systems.modules.Module;
import vampdev.vampclient.systems.modules.Modules;
import vampdev.vampclient.utils.misc.NbtUtils;
import net.minecraft.item.Items;
import vampdev.vampclient.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModulesScreen extends TabScreen {
    public ModulesScreen(GuiTheme theme) {
        super(theme, Tabs.get().get(0));
    }

    @Override
    public void initWidgets() {
        add(new WCategoryController());

        // Help
        WVerticalList help = add(theme.verticalList()).pad(4).bottom().widget();
        help.add(theme.label("Left click - Toggle module"));
        help.add(theme.label("Right click - Open module settings"));
    }

    // Category

    protected WWindow createCategory(WContainer c, Category category) {
        WWindow w = theme.window(category.name);
        w.id = category.name;
        w.padding = 0;
        w.spacing = 0;

        if (theme.categoryIcons()) {
            w.beforeHeaderInit = wContainer -> wContainer.add(theme.item(category.icon)).pad(2);
        }

        c.add(w);
        w.view.scrollOnlyWhenMouseOver = true;
        w.view.hasScrollBar = false;
        w.view.spacing = 0;

        for (Module module : Modules.get().getGroup(category)) {
            w.add(theme.module(module)).expandX().widget().tooltip = module.description;
        }

        return w;
    }

    // Search

    protected void createSearchW(WContainer w, String text) {
        if (!text.isEmpty()) {
            // Titles
            Set<Module> modules = Modules.get().searchTitles(text);

            if (modules.size() > 0) {
                WSection section = w.add(theme.section("Modules")).expandX().widget();
                section.spacing = 0;

                for (Module module : modules) {
                    section.add(theme.module(module)).expandX();
                }
            }

            // Settings
            modules = Modules.get().searchSettingTitles(text);

            if (modules.size() > 0) {
                WSection section = w.add(theme.section("Settings")).expandX().widget();
                section.spacing = 0;

                for (Module module : modules) {
                    section.add(theme.module(module)).expandX();
                }
            }
        }
    }

    protected WWindow createSearch(WContainer c) {
        WWindow w = theme.window("Search");
        w.id = "search";

        if (theme.categoryIcons()) {
            w.beforeHeaderInit = wContainer -> wContainer.add(theme.item(Items.COMPASS.getDefaultStack())).pad(2);
        }

        c.add(w);
        w.view.scrollOnlyWhenMouseOver = true;
        w.view.hasScrollBar = false;
        w.view.maxHeight -= 20;

        WVerticalList l = theme.verticalList();

        WTextBox text = w.add(theme.textBox("")).minWidth(140).expandX().widget();
        text.setFocused(true);
        text.action = () -> {
            l.clear();
            createSearchW(l, text.get());
        };

        w.add(l).expandX();
        createSearchW(l, text.get());

        return w;
    }

    @Override
    public boolean toClipboard() {
        return NbtUtils.toClipboard(Modules.get());
    }

    @Override
    public boolean fromClipboard() {
        return NbtUtils.fromClipboard(Modules.get());
    }

    @Override
    public void reload() {}

    // Stuff

    protected class WCategoryController extends WContainer {
        public final List<WWindow> windows = new ArrayList<>();

        @Override
        public void init() {
            for (Category category : Modules.loopCategories()) {
                windows.add(createCategory(this, category));
            }

            windows.add(createSearch(this));
        }

        @Override
        protected void onCalculateWidgetPositions() {
            double pad = theme.scale(4);
            double h = theme.scale(40);

            double x = this.x + pad;
            double y = this.y;

            for (Cell<?> cell : cells) {
                double windowWidth = Utils.getWindowWidth();
                double windowHeight = Utils.getWindowHeight();

                if (x + cell.width > windowWidth) {
                    x = x + pad;
                    y += h;
                }

                if (x > windowWidth) {
                    x = windowWidth / 2.0 - cell.width / 2.0;
                    if (x < 0) x = 0;
                }
                if (y > windowHeight) {
                    y = windowHeight / 2.0 - cell.height / 2.0;
                    if (y < 0) y = 0;
                }

                cell.x = x;
                cell.y = y;

                cell.width = cell.widget().width;
                cell.height = cell.widget().height;

                cell.alignWidget();

                x += cell.width + pad;
            }
        }
    }
}
