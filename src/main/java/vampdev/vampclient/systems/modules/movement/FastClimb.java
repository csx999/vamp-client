

package vampdev.vampclient.systems.modules.movement;

import vampdev.vampclient.events.world.TickEvent;
import vampdev.vampclient.settings.DoubleSetting;
import vampdev.vampclient.settings.Setting;
import vampdev.vampclient.settings.SettingGroup;
import vampdev.vampclient.systems.modules.Categories;
import vampdev.vampclient.systems.modules.Module;
import vampdev.vampclient.eventbus.EventHandler;
import net.minecraft.util.math.Vec3d;

public class FastClimb extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> speed = sgGeneral.add(new DoubleSetting.Builder()
            .name("climb-speed")
            .description("Your climb speed.")
            .defaultValue(0.2872)
            .min(0.0)
            .build()
    );

    public FastClimb() {
        super(Categories.Movement, "fast-climb", "Allows you to climb faster.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (!mc.player.isClimbing() || !mc.player.horizontalCollision) return;
        if (mc.player.input.movementForward == 0 && mc.player.input.movementSideways == 0) return;

        Vec3d velocity = mc.player.getVelocity();
        mc.player.setVelocity(velocity.x, speed.get(), velocity.z);
    }
}
