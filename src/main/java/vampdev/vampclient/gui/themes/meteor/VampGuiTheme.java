

package vampdev.vampclient.gui.themes.meteor;

import vampdev.vampclient.gui.DefaultSettingsWidgetFactory;
import vampdev.vampclient.gui.GuiTheme;
import vampdev.vampclient.gui.WidgetScreen;
import vampdev.vampclient.gui.renderer.packer.GuiTexture;
import vampdev.vampclient.gui.themes.meteor.widgets.*;
import vampdev.vampclient.gui.themes.meteor.widgets.input.WVampDropdown;
import vampdev.vampclient.gui.themes.meteor.widgets.input.WVampSlider;
import vampdev.vampclient.gui.themes.meteor.widgets.input.WVampTextBox;
import vampdev.vampclient.gui.themes.meteor.widgets.pressable.*;
import vampdev.vampclient.gui.utils.AlignmentX;
import vampdev.vampclient.gui.utils.CharFilter;
import vampdev.vampclient.gui.widgets.*;
import vampdev.vampclient.gui.widgets.containers.WSection;
import vampdev.vampclient.gui.widgets.containers.WView;
import vampdev.vampclient.gui.widgets.containers.WWindow;
import vampdev.vampclient.gui.widgets.input.WDropdown;
import vampdev.vampclient.gui.widgets.input.WSlider;
import vampdev.vampclient.gui.widgets.input.WTextBox;
import vampdev.vampclient.gui.widgets.pressable.*;
import vampdev.vampclient.renderer.text.TextRenderer;
import vampdev.vampclient.systems.accounts.Account;
import vampdev.vampclient.systems.modules.Module;
import vampdev.vampclient.utils.render.color.Color;
import vampdev.vampclient.utils.render.color.SettingColor;
import vampdev.vampclient.VampClient;
import vampdev.vampclient.settings.*;

public class VampGuiTheme extends GuiTheme {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgColors = settings.createGroup("Colors");
    private final SettingGroup sgTextColors = settings.createGroup("Text");
    private final SettingGroup sgBackgroundColors = settings.createGroup("Background");
    private final SettingGroup sgOutline = settings.createGroup("Outline");
    private final SettingGroup sgSeparator = settings.createGroup("Separator");
    private final SettingGroup sgScrollbar = settings.createGroup("Scrollbar");
    private final SettingGroup sgSlider = settings.createGroup("Slider");

    // General

    public final Setting<Double> scale = sgGeneral.add(new DoubleSetting.Builder()
            .name("scale")
            .description("Scale of the GUI.")
            .defaultValue(1)
            .min(0.75)
            .sliderRange(0.75, 4)
            .onSliderRelease()
            .onChanged(aDouble -> {
                if (VampClient.mc.currentScreen instanceof WidgetScreen) ((WidgetScreen) VampClient.mc.currentScreen).invalidate();
            })
            .build()
    );

    public final Setting<AlignmentX> moduleAlignment = sgGeneral.add(new EnumSetting.Builder<AlignmentX>()
            .name("module-alignment")
            .description("How module titles are aligned.")
            .defaultValue(AlignmentX.Center)
            .build()
    );

    public final Setting<Boolean> categoryIcons = sgGeneral.add(new BoolSetting.Builder()
            .name("category-icons")
            .description("Adds item icons to module categories.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Boolean> hideHUD = sgGeneral.add(new BoolSetting.Builder()
            .name("hide-HUD")
            .description("Hide HUD when in GUI.")
            .defaultValue(false)
            .onChanged(v -> {
                if (VampClient.mc.currentScreen instanceof WidgetScreen) VampClient.mc.options.hudHidden = v;
            })
            .build()
    );

    // Colors

    public final Setting<SettingColor> accentColor = color("accent", "Main color of the GUI.", new SettingColor(0, 0, 0));
    public final Setting<SettingColor> checkboxColor = color("checkbox", "Color of checkbox.", new SettingColor(255, 255, 255));
    public final Setting<SettingColor> plusColor = color("plus", "Color of plus button.", new SettingColor(255, 255, 255));
    public final Setting<SettingColor> minusColor = color("minus", "Color of minus button.", new SettingColor(255, 255, 255));

    // Text

    public final Setting<SettingColor> textColor = color(sgTextColors, "text", "Color of text.", new SettingColor(255, 255, 255));
    public final Setting<SettingColor> textSecondaryColor = color(sgTextColors, "text-secondary-text", "Color of secondary text.", new SettingColor(150, 150, 150));
    public final Setting<SettingColor> textHighlightColor = color(sgTextColors, "text-highlight", "Color of text highlighting.", new SettingColor(45, 125, 245, 100));
    public final Setting<SettingColor> titleTextColor = color(sgTextColors, "title-text", "Color of title text.", new SettingColor(255, 255, 255));
    public final Setting<SettingColor> loggedInColor = color(sgTextColors, "logged-in-text", "Color of logged in account name.", new SettingColor(45, 225, 45));

    // Background

    public final ThreeStateColorSetting backgroundColor = new ThreeStateColorSetting(
            sgBackgroundColors,
            "background",
            new SettingColor(0, 0, 0, 200),
            new SettingColor(10, 10, 10, 200),
            new SettingColor(10, 10, 10, 200)
    );

    public final Setting<SettingColor> moduleBackground = color(sgBackgroundColors, "module-background", "Color of module background when active.", new SettingColor(50, 50, 50));

    // Outline

    public final ThreeStateColorSetting outlineColor = new ThreeStateColorSetting(
            sgOutline,
            "outline",
            new SettingColor(0, 0, 0),
            new SettingColor(10, 10, 10),
            new SettingColor(20, 20, 20)
    );

    // Separator

    public final Setting<SettingColor> separatorText = color(sgSeparator, "separator-text", "Color of separator text", new SettingColor(255, 255, 255));
    public final Setting<SettingColor> separatorCenter = color(sgSeparator, "separator-center", "Center color of separators.", new SettingColor(255, 255, 255));
    public final Setting<SettingColor> separatorEdges = color(sgSeparator, "separator-edges", "Color of separator edges.", new SettingColor(225, 225, 225, 150));

    // Scrollbar

    public final ThreeStateColorSetting scrollbarColor = new ThreeStateColorSetting(
            sgScrollbar,
            "Scrollbar",
            new SettingColor(30, 30, 30, 200),
            new SettingColor(40, 40, 40, 200),
            new SettingColor(50, 50, 50, 200)
    );

    // Slider

    public final ThreeStateColorSetting sliderHandle = new ThreeStateColorSetting(
            sgSlider,
            "slider-handle",
            new SettingColor(0, 0, 0),
            new SettingColor(6, 6, 6),
            new SettingColor(6, 6, 6)
    );

    public final Setting<SettingColor> sliderLeft = color(sgSlider, "slider-left", "Color of slider left part.", new SettingColor(6, 6, 6));
    public final Setting<SettingColor> sliderRight = color(sgSlider, "slider-right", "Color of slider right part.", new SettingColor(6, 6, 6));

    public VampGuiTheme() {
        super("Vamp");

        settingsFactory = new DefaultSettingsWidgetFactory(this);
    }

    private Setting<SettingColor> color(SettingGroup group, String name, String description, SettingColor color) {
        return group.add(new ColorSetting.Builder()
                .name(name + "-color")
                .description(description)
                .defaultValue(color)
                .build());
    }
    private Setting<SettingColor> color(String name, String description, SettingColor color) {
        return color(sgColors, name, description, color);
    }

    // Widgets

    @Override
    public WWindow window(String title) {
        return w(new WVampWindow(title));
    }

    @Override
    public WLabel label(String text, boolean title, double maxWidth) {
        if (maxWidth == 0) return w(new WVampLabel(text, title));
        return w(new WVampMultiLabel(text, title, maxWidth));
    }

    @Override
    public WHorizontalSeparator horizontalSeparator(String text) {
        return w(new WVampHorizontalSeparator(text));
    }

    @Override
    public WVerticalSeparator verticalSeparator() {
        return w(new WVampVerticalSeparator());
    }

    @Override
    protected WButton button(String text, GuiTexture texture) {
        return w(new WVampButton(text, texture));
    }

    @Override
    public WMinus minus() {
        return w(new WVampMinus());
    }

    @Override
    public WPlus plus() {
        return w(new WVampPlus());
    }

    @Override
    public WCheckbox checkbox(boolean checked) {
        return w(new WVampCheckbox(checked));
    }

    @Override
    public WSlider slider(double value, double min, double max) {
        return w(new WVampSlider(value, min, max));
    }

    @Override
    public WTextBox textBox(String text, CharFilter filter) {
        return w(new WVampTextBox(text, filter));
    }

    @Override
    public <T> WDropdown<T> dropdown(T[] values, T value) {
        return w(new WVampDropdown<>(values, value));
    }

    @Override
    public WTriangle triangle() {
        return w(new WVampTriangle());
    }

    @Override
    public WTooltip tooltip(String text) {
        return w(new WVampTooltip(text));
    }

    @Override
    public WView view() {
        return w(new WVampView());
    }

    @Override
    public WSection section(String title, boolean expanded, WWidget headerWidget) {
        return w(new WVampSection(title, expanded, headerWidget));
    }

    @Override
    public WAccount account(WidgetScreen screen, Account<?> account) {
        return w(new WVampAccount(screen, account));
    }

    @Override
    public WWidget module(Module module) {
        return w(new WVampModule(module));
    }

    @Override
    public WQuad quad(Color color) {
        return w(new WVampQuad(color));
    }

    @Override
    public WTopBar topBar() {
        return w(new WVampTopBar());
    }

    // Colors

    @Override
    public Color textColor() {
        return textColor.get();
    }

    @Override
    public Color textSecondaryColor() {
        return textSecondaryColor.get();
    }

    // Other

    @Override
    public TextRenderer textRenderer() {
        return TextRenderer.get();
    }

    @Override
    public double scale(double value) {
        return value * scale.get();
    }

    @Override
    public boolean categoryIcons() {
        return categoryIcons.get();
    }

    @Override
    public boolean hideHUD() {
        return hideHUD.get();
    }

    public class ThreeStateColorSetting {
        private final Setting<SettingColor> normal, hovered, pressed;

        public ThreeStateColorSetting(SettingGroup group, String name, SettingColor c1, SettingColor c2, SettingColor c3) {
            normal = color(group, name, "Color of " + name + ".", c1);
            hovered = color(group, "hovered-" + name, "Color of " + name + " when hovered.", c2);
            pressed = color(group, "pressed-" + name, "Color of " + name + " when pressed.", c3);
        }

        public SettingColor get() {
            return normal.get();
        }

        public SettingColor get(boolean pressed, boolean hovered, boolean bypassDisableHoverColor) {
            if (pressed) return this.pressed.get();
            return (hovered && (bypassDisableHoverColor || !disableHoverColor)) ? this.hovered.get() : this.normal.get();
        }

        public SettingColor get(boolean pressed, boolean hovered) {
            return get(pressed, hovered, false);
        }
    }
}
