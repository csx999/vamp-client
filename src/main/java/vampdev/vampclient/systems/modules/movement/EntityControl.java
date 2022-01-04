

package vampdev.vampclient.systems.modules.movement;

//Created by squidoodly 10/07/2020

import vampdev.vampclient.events.world.TickEvent;
import vampdev.vampclient.mixin.ClientPlayerEntityAccessor;
import vampdev.vampclient.mixininterface.IHorseBaseEntity;
import vampdev.vampclient.settings.BoolSetting;
import vampdev.vampclient.settings.Setting;
import vampdev.vampclient.settings.SettingGroup;
import vampdev.vampclient.systems.modules.Categories;
import vampdev.vampclient.systems.modules.Module;
import vampdev.vampclient.utils.Utils;
import vampdev.vampclient.eventbus.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.HorseBaseEntity;

public class EntityControl extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> maxJump = sgGeneral.add(new BoolSetting.Builder()
            .name("max-jump")
            .description("Sets jump power to maximum.")
            .defaultValue(true)
            .build()
    );

    public EntityControl() {
        super(Categories.Movement, "entity-control", "Lets you control rideable entities without a saddle.");
    }

    @Override
    public void onDeactivate() {
        if (!Utils.canUpdate() || mc.world.getEntities() == null) return;

        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof HorseBaseEntity) ((IHorseBaseEntity) entity).setSaddled(false);
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof HorseBaseEntity) ((IHorseBaseEntity) entity).setSaddled(true);
        }

        if (maxJump.get()) ((ClientPlayerEntityAccessor) mc.player).setMountJumpStrength(1);
    }
}
