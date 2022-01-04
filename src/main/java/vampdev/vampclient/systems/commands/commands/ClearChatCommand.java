

package vampdev.vampclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import vampdev.vampclient.systems.commands.Command;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class ClearChatCommand extends Command {
    public ClearChatCommand() {
        super("clear-chat", "Clears your chat.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            mc.inGameHud.getChatHud().clear(false);
            return SINGLE_SUCCESS;
        });
    }
}
