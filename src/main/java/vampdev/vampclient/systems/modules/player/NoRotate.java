

package vampdev.vampclient.systems.modules.player;

import vampdev.vampclient.events.packets.PacketEvent;
import vampdev.vampclient.mixin.PlayerPositionLookS2CPacketAccessor;
import vampdev.vampclient.systems.modules.Categories;
import vampdev.vampclient.systems.modules.Module;
import vampdev.vampclient.eventbus.EventHandler;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

public class NoRotate extends Module {
    public NoRotate() {
        super(Categories.Player, "no-rotate", "Attempts to block rotations sent from server to client.");
    }

    @EventHandler
    private void onReceivePacket(PacketEvent.Receive event) {
        if (event.packet instanceof PlayerPositionLookS2CPacket) {
            ((PlayerPositionLookS2CPacketAccessor) event.packet).setPitch(mc.player.getPitch());
            ((PlayerPositionLookS2CPacketAccessor) event.packet).setYaw(mc.player.getYaw());
        }
    }
}
