

package vampdev.vampclient.systems.modules.render.hud;

import vampdev.vampclient.events.render.Render2DEvent;
import vampdev.vampclient.gui.GuiTheme;
import vampdev.vampclient.gui.GuiThemes;
import vampdev.vampclient.gui.renderer.GuiRenderer;
import vampdev.vampclient.gui.screens.HudElementScreen;
import vampdev.vampclient.gui.tabs.Tab;
import vampdev.vampclient.gui.tabs.Tabs;
import vampdev.vampclient.gui.tabs.builtin.HudTab;
import vampdev.vampclient.gui.widgets.WWidget;
import vampdev.vampclient.gui.widgets.containers.WTable;
import vampdev.vampclient.gui.widgets.pressable.WButton;
import vampdev.vampclient.settings.*;
import vampdev.vampclient.systems.modules.Categories;
import vampdev.vampclient.systems.modules.Module;
import vampdev.vampclient.systems.modules.render.hud.modules.*;
import vampdev.vampclient.utils.render.AlignmentX;
import vampdev.vampclient.utils.render.AlignmentY;
import vampdev.vampclient.utils.render.color.SettingColor;
import vampdev.vampclient.eventbus.EventHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

import java.util.ArrayList;
import java.util.List;

public class HUD extends Module {
    private static final HudRenderer RENDERER = new HudRenderer();

    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgEditor = settings.createGroup("Editor");

    // General

    public final Setting<Double> scale = sgGeneral.add(new DoubleSetting.Builder()
        .name("scale")
        .description("Scale of the HUD.")
        .defaultValue(1)
        .min(0.75)
        .sliderMin(0.75)
        .sliderMax(4)
        .build()
    );

    public final Setting<SettingColor> primaryColor = sgGeneral.add(new ColorSetting.Builder()
        .name("primary-color")
        .description("Primary color of text.")
        .defaultValue(new SettingColor(255, 255, 255))
        .build()
    );

    public final Setting<SettingColor> secondaryColor = sgGeneral.add(new ColorSetting.Builder()
        .name("secondary-color")
        .description("Secondary color of text.")
        .defaultValue(new SettingColor(175, 175, 175))
        .build()
    );

    public final Setting<Boolean> mountHud = sgGeneral.add(new BoolSetting.Builder()
        .name("mount-hud")
        .description("Display xp bar and hunger when riding.")
        .defaultValue(true)
        .build()
    );

    // Editor

    public final Setting<Integer> snappingRange = sgEditor.add(new IntSetting.Builder()
        .name("snapping-range")
        .description("Snapping range in editor.")
        .defaultValue(6)
        .build()
    );

    public final List<HudElement> elements = new ArrayList<>();
    public final HudElementLayer topLeft, topCenter, topRight, bottomLeft, bottomCenter, bottomRight;

    public final Runnable reset = () -> {
        align();
        elements.forEach(element -> {
            element.active = element.defaultActive;
            element.settings.forEach(group -> group.forEach(Setting::reset));
        });
    };

    public HUD() {
        super(Categories.Render, "HUD", "In game overlay.");

        // Top Left
        topLeft = new HudElementLayer(RENDERER, elements, AlignmentX.Left, AlignmentY.Top, 2, 2);
        topLeft.add(new LogoHud(this));
        topLeft.add(new WatermarkHud(this));
        topLeft.add(new FpsHud(this));
        topLeft.add(new PingHud(this));
        topLeft.add(new TpsHud(this));
        topLeft.add(new SpeedHud(this));
        topLeft.add(new BiomeHud(this));
        topLeft.add(new TimeHud(this));
        topLeft.add(new ServerHud(this));
        topLeft.add(new DurabilityHud(this));
        topLeft.add(new BreakingBlockHud(this));
        topLeft.add(new LookingAtHud(this));
        topLeft.add(new ModuleInfoHud(this));
        topLeft.add(new TextRadarHud(this));

        // Top Center
        topCenter = new HudElementLayer(RENDERER, elements, AlignmentX.Center, AlignmentY.Top, 0, 2);
        topCenter.add(new InventoryViewerHud(this));
        topCenter.add(new WelcomeHud(this));
        topCenter.add(new LagNotifierHud(this));

        // Top Right
        topRight = new HudElementLayer(RENDERER, elements, AlignmentX.Right, AlignmentY.Top, 2, 2);
        topRight.add(new ActiveModulesHud(this));


        // Bottom Left
        bottomLeft = new HudElementLayer(RENDERER, elements, AlignmentX.Left, AlignmentY.Bottom, 2, 2);
        bottomLeft.add(new PlayerModelHud(this));

        // Bottom Center
        bottomCenter = new HudElementLayer(RENDERER, elements, AlignmentX.Center, AlignmentY.Bottom, 48, 64);
        bottomCenter.add(new ArmorHud(this));
        bottomCenter.add(new CompassHud(this));
        bottomCenter.add(new ContainerViewerHud(this));
        bottomCenter.add(new TotemHud(this));

        // Bottom Right
        bottomRight = new HudElementLayer(RENDERER, elements, AlignmentX.Right, AlignmentY.Bottom, 2, 2);
        bottomRight.add(new PositionHud(this));
        bottomRight.add(new RotationHud(this));
        bottomRight.add(new PotionTimersHud(this));
        bottomRight.add(new HoleHud(this));
        bottomRight.add(new CombatHud(this));

        align();
    }

    private void align() {
        RENDERER.begin(scale.get(), 0, true);

        topLeft.align();
        topCenter.align();
        topRight.align();
        bottomLeft.align();
        bottomCenter.align();
        bottomRight.align();

        RENDERER.end();
    }

    @EventHandler
    public void onRender(Render2DEvent event) {
        if (mc.options.debugEnabled || mc.options.hudHidden) return;

        RENDERER.begin(scale.get(), event.frameTime, false);

        for (HudElement element : elements) {
            if (element.active || HudTab.INSTANCE.isScreen(mc.currentScreen) || mc.currentScreen instanceof HudElementScreen) {
                element.update(RENDERER);
                element.render(RENDERER);
            }
        }

        RENDERER.end();
    }

    @Override
    public WWidget getWidget(GuiTheme theme) {
        WTable table = theme.table();

        // because some people are just monkies
        WButton openEditor = table.add(theme.button(GuiRenderer.EDIT)).widget();
        openEditor.action = this::openHudEditor;
        table.add(theme.label("Opens HUD editor."));
        table.row();

        WButton reset = table.add(theme.button(GuiRenderer.RESET)).widget();
        reset.action = this.reset;
        table.add(theme.label("Resets positions (do this after changing scale)."));
        table.row();

        return table;
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = super.toTag();

        NbtList modulesTag = new NbtList();
        for (HudElement module : elements) modulesTag.add(module.toTag());
        tag.put("modules", modulesTag);

        return tag;
    }

    @Override
    public Module fromTag(NbtCompound tag) {
        if (tag.contains("modules")) {
            NbtList modulesTag = tag.getList("modules", 10);

            for (NbtElement t : modulesTag) {
                NbtCompound moduleTag = (NbtCompound) t;

                HudElement module = getModule(moduleTag.getString("name"));
                if (module != null) module.fromTag(moduleTag);
            }
        }

        return super.fromTag(tag);
    }

    private HudElement getModule(String name) {
        for (HudElement module : elements) {
            if (module.name.equals(name)) return module;
        }

        return null;
    }

    private void openHudEditor() {
        HudTab hudTab = null;
        for (Tab tab : Tabs.get()) {
            if (tab instanceof HudTab) {
                hudTab = (HudTab) tab;
                break;
            }
        }
        if (hudTab == null) return;
        hudTab.openScreen(GuiThemes.get());
    }

    public boolean mountHud() {
        return isActive() && mountHud.get();
    }
}
