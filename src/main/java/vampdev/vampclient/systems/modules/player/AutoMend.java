

package vampdev.vampclient.systems.modules.player;

import vampdev.vampclient.events.world.TickEvent;
import vampdev.vampclient.settings.BoolSetting;
import vampdev.vampclient.settings.ItemListSetting;
import vampdev.vampclient.settings.Setting;
import vampdev.vampclient.settings.SettingGroup;
import vampdev.vampclient.systems.modules.Categories;
import vampdev.vampclient.systems.modules.Module;
import vampdev.vampclient.utils.player.InvUtils;
import vampdev.vampclient.eventbus.EventHandler;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class AutoMend extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<List<Item>> blacklist = sgGeneral.add(new ItemListSetting.Builder()
            .name("blacklist")
            .description("Item blacklist.")
            .filter(Item::isDamageable)
            .build()
    );

    private final Setting<Boolean> force = sgGeneral.add(new BoolSetting.Builder()
            .name("force")
            .description("Replaces item in offhand even if there is some other non-repairable item.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> autoDisable = sgGeneral.add(new BoolSetting.Builder()
            .name("auto-disable")
            .description("Automatically disables when there are no more items to repair.")
            .defaultValue(true)
            .build()
    );

    private boolean didMove;

    public AutoMend() {
        super(Categories.Player, "auto-mend", "Automatically replaces items in your offhand with mending when fully repaired.");
    }

    @Override
    public void onActivate() {
        didMove = false;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (shouldWait()) return;

        int slot = getSlot();

        if (slot == -1) {
            if (autoDisable.get()) {
                info("Repaired all items, disabling");

                if (didMove) {
                    int emptySlot = getEmptySlot();
                    InvUtils.move().fromOffhand().to(emptySlot);
                }

                toggle();
            }
        }
        else {
            InvUtils.move().from(slot).toOffhand();
            didMove = true;
        }
    }

    private boolean shouldWait() {
        ItemStack itemStack = mc.player.getOffHandStack();

        if (itemStack.isEmpty()) return false;

        if (EnchantmentHelper.getLevel(Enchantments.MENDING, itemStack) > 0) {
            return itemStack.getDamage() != 0;
        }

        return !force.get();
    }

    private int getSlot() {
        for (int i = 0; i < mc.player.getInventory().main.size(); i++) {
            ItemStack itemStack = mc.player.getInventory().getStack(i);
            if (blacklist.get().contains(itemStack.getItem())) continue;

            if (EnchantmentHelper.getLevel(Enchantments.MENDING, itemStack) > 0 && itemStack.getDamage() > 0) {
                return i;
            }
        }

        return -1;
    }

    private int getEmptySlot() {
        for (int i = 0; i < mc.player.getInventory().main.size(); i++) {
            if (mc.player.getInventory().getStack(i).isEmpty()) return i;
        }

        return -1;
    }
}
