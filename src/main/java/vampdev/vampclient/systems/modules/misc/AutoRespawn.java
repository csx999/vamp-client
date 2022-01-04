

package vampdev.vampclient.systems.modules.misc;

import vampdev.vampclient.events.game.OpenScreenEvent;
import vampdev.vampclient.systems.modules.Categories;
import vampdev.vampclient.systems.modules.Module;
import vampdev.vampclient.systems.modules.Modules;
import vampdev.vampclient.systems.modules.render.WaypointsModule;
import vampdev.vampclient.eventbus.EventHandler;
import vampdev.vampclient.eventbus.EventPriority;
import net.minecraft.client.gui.screen.DeathScreen;

public class AutoRespawn extends Module {
    public AutoRespawn() {
        super(Categories.Player, "auto-respawn", "Automatically respawns after death.");
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onOpenScreenEvent(OpenScreenEvent event) {
        if (!(event.screen instanceof DeathScreen)) return;

        Modules.get().get(WaypointsModule.class).addDeath(mc.player.getPos());
        mc.player.requestRespawn();
        event.cancel();
    }
}
