

package vampdev.vampclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import vampdev.vampclient.VampClient;
import vampdev.vampclient.events.meteor.KeyEvent;
import vampdev.vampclient.systems.commands.Command;
import vampdev.vampclient.systems.commands.arguments.PlayerArgumentType;
import vampdev.vampclient.eventbus.EventHandler;
import net.minecraft.command.CommandSource;
import net.minecraft.text.LiteralText;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class SpectateCommand extends Command {

    private final StaticListener shiftListener = new StaticListener();

    public SpectateCommand() {
        super("spectate", "Allows you to spectate nearby players");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(literal("reset").executes(context -> {
            mc.setCameraEntity(mc.player);
            return SINGLE_SUCCESS;
        }));

        builder.then(argument("player", PlayerArgumentType.player()).executes(context -> {
            mc.setCameraEntity(PlayerArgumentType.getPlayer(context));
            mc.player.sendMessage(new LiteralText("Sneak to un-spectate."), true);
            VampClient.EVENT_BUS.subscribe(shiftListener);
            return SINGLE_SUCCESS;
        }));
    }

    private class StaticListener {
        @EventHandler
        private void onKey(KeyEvent event) {
            if (mc.options.keySneak.matchesKey(event.key, 0) || mc.options.keySneak.matchesMouse(event.key)) {
                mc.setCameraEntity(mc.player);
                event.cancel();
                VampClient.EVENT_BUS.unsubscribe(this);
            }
        }
    }
}
