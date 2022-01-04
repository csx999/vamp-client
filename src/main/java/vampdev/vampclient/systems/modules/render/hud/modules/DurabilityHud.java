

package vampdev.vampclient.systems.modules.render.hud.modules;

import vampdev.vampclient.systems.modules.render.hud.HUD;

public class DurabilityHud extends DoubleTextHudElement {
    public DurabilityHud(HUD hud) {
        super(hud, "durability", "Displays durability of the item you are holding.", "Durability: ");
    }

    @Override
    protected String getRight() {
        if (isInEditor()) return "159";

        if (!mc.player.getMainHandStack().isEmpty() && mc.player.getMainHandStack().isDamageable()) {
            return String.valueOf(mc.player.getMainHandStack().getMaxDamage() - mc.player.getMainHandStack().getDamage());
        }

        return "";
    }
}
