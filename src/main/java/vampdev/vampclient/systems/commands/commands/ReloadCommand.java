

package vampdev.vampclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import vampdev.vampclient.systems.Systems;
import vampdev.vampclient.systems.commands.Command;
import vampdev.vampclient.utils.network.Capes;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class ReloadCommand extends Command {
    public ReloadCommand() {
        super("reload", "Reloads the config, modules, friends, macros, accounts and capes.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            Systems.load();
            Capes.init();

            return SINGLE_SUCCESS;
        });
    }
}
