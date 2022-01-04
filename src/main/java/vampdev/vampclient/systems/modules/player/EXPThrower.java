

package vampdev.vampclient.systems.modules.player;

import vampdev.vampclient.events.world.TickEvent;
import vampdev.vampclient.systems.modules.Categories;
import vampdev.vampclient.systems.modules.Module;
import vampdev.vampclient.utils.player.FindItemResult;
import vampdev.vampclient.utils.player.InvUtils;
import vampdev.vampclient.utils.player.Rotations;
import vampdev.vampclient.eventbus.EventHandler;
import net.minecraft.item.Items;

public class EXPThrower extends Module {
    public EXPThrower() {
        super(Categories.Player, "exp-thrower", "Automatically throws XP bottles from your hotbar.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        FindItemResult exp = InvUtils.findInHotbar(Items.EXPERIENCE_BOTTLE);
        if (!exp.found()) return;

        Rotations.rotate(mc.player.getYaw(), 90, () -> {
            if (exp.getHand() != null) {
                mc.interactionManager.interactItem(mc.player, mc.world, exp.getHand());
            }
            else {
                InvUtils.swap(exp.getSlot(), true);
                mc.interactionManager.interactItem(mc.player, mc.world, exp.getHand());
                InvUtils.swapBack();
            }
        });
    }
}
