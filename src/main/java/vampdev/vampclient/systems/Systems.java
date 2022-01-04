

package vampdev.vampclient.systems;

import vampdev.vampclient.VampClient;
import vampdev.vampclient.events.game.GameLeftEvent;
import vampdev.vampclient.systems.accounts.Accounts;
import vampdev.vampclient.systems.commands.Commands;
import vampdev.vampclient.systems.config.Config;
import vampdev.vampclient.systems.friends.Friends;
import vampdev.vampclient.systems.macros.Macros;
import vampdev.vampclient.systems.modules.Modules;
import vampdev.vampclient.systems.profiles.Profiles;
import vampdev.vampclient.systems.proxies.Proxies;
import vampdev.vampclient.systems.waypoints.Waypoints;
import vampdev.vampclient.eventbus.EventHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Systems {
    @SuppressWarnings("rawtypes")
    private static final Map<Class<? extends System>, System<?>> systems = new HashMap<>();
    private static final List<Runnable> preLoadTasks = new ArrayList<>(1);

    public static void addPreLoadTask(Runnable task) {
        preLoadTasks.add(task);
    }

    public static void init() {
        System<?> config = add(new Config());
        config.init();
        config.load();

        add(new Modules());
        add(new Commands());
        add(new Friends());
        add(new Macros());
        add(new Accounts());
        add(new Waypoints());
        add(new Profiles());
        add(new Proxies());

        VampClient.EVENT_BUS.subscribe(Systems.class);
    }

    private static System<?> add(System<?> system) {
        systems.put(system.getClass(), system);
        VampClient.EVENT_BUS.subscribe(system);
        system.init();

        return system;
    }

    // save/load

    @EventHandler
    private static void onGameLeft(GameLeftEvent event) {
        save();
    }

    public static void save(File folder) {
        long start = java.lang.System.currentTimeMillis();
        VampClient.LOG.info("Saving");

        for (System<?> system : systems.values()) system.save(folder);

        VampClient.LOG.info("Saved in {} milliseconds.", java.lang.System.currentTimeMillis() - start);
    }

    public static void save() {
        save(null);
    }

    public static void load(File folder) {
        long start = java.lang.System.currentTimeMillis();
        VampClient.LOG.info("Loading");

        for (Runnable task : preLoadTasks) task.run();
        for (System<?> system : systems.values()) system.load(folder);

        VampClient.LOG.info("Loaded in {} milliseconds", java.lang.System.currentTimeMillis() - start);
    }

    public static void load() {
        load(null);
    }

    @SuppressWarnings("unchecked")
    public static <T extends System<?>> T get(Class<T> klass) {
        return (T) systems.get(klass);
    }
}
