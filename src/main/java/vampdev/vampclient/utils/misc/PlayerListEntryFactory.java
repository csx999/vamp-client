

package vampdev.vampclient.utils.misc;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;

public class PlayerListEntryFactory extends PlayerListS2CPacket {
    private static final PlayerListEntryFactory INSTANCE = new PlayerListEntryFactory();

    public PlayerListEntryFactory() {
        super(null, new ServerPlayerEntity[0]);
    }

    public static Entry create(GameProfile profile, int latency, GameMode gameMode, Text displayName) {
        return INSTANCE._create(profile, latency, gameMode, displayName);
    }

    private Entry _create(GameProfile profile, int latency, GameMode gameMode, Text displayName) {
        return new Entry(profile, latency, gameMode, displayName);
    }
}
