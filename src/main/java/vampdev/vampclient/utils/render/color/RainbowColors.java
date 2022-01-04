

package vampdev.vampclient.utils.render.color;

import vampdev.vampclient.VampClient;
import vampdev.vampclient.events.world.TickEvent;
import vampdev.vampclient.gui.GuiThemes;
import vampdev.vampclient.gui.WidgetScreen;
import vampdev.vampclient.gui.tabs.builtin.ConfigTab;
import vampdev.vampclient.settings.ColorSetting;
import vampdev.vampclient.settings.Setting;
import vampdev.vampclient.settings.SettingGroup;
import vampdev.vampclient.systems.waypoints.Waypoint;
import vampdev.vampclient.systems.waypoints.Waypoints;
import vampdev.vampclient.utils.Init;
import vampdev.vampclient.utils.InitStage;
import vampdev.vampclient.utils.misc.UnorderedArrayList;
import vampdev.vampclient.eventbus.EventHandler;

import java.util.List;

import static vampdev.vampclient.VampClient.mc;

public class RainbowColors {
    private static final List<Setting<SettingColor>> colorSettings = new UnorderedArrayList<>();
    private static final List<SettingColor> colors = new UnorderedArrayList<>();
    private static final List<Runnable> listeners = new UnorderedArrayList<>();

    public static RainbowColor GLOBAL;

    @Init(stage = InitStage.Pre)
    public static void init() {
        VampClient.EVENT_BUS.subscribe(RainbowColors.class);

        GLOBAL = new RainbowColor().setSpeed(ConfigTab.rainbowSpeed.get() / 100);
    }

    public static void addSetting(Setting<SettingColor> setting) {
        colorSettings.add(setting);
    }

    public static void removeSetting(Setting<SettingColor> setting) {
        colorSettings.remove(setting);
    }

    public static void add(SettingColor color) {
        colors.add(color);
    }

    public static void register(Runnable runnable) {
        listeners.add(runnable);
    }

    @EventHandler
    private static void onTick(TickEvent.Post event) {
        GLOBAL.getNext();

        for (Setting<SettingColor> setting : colorSettings) {
            if (setting.module == null || setting.module.isActive()) setting.get().update();
        }

        for (SettingColor color : colors) {
            color.update();
        }

        for (Waypoint waypoint : Waypoints.get()) {
            waypoint.color.update();
        }

        if (mc.currentScreen instanceof WidgetScreen) {
            for (SettingGroup group : GuiThemes.get().settings) {
                for (Setting<?> setting : group) {
                    if (setting instanceof ColorSetting) ((SettingColor) setting.get()).update();
                }
            }
        }

        for (Runnable listener : listeners) listener.run();
    }
}
