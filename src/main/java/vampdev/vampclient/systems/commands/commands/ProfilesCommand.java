

package vampdev.vampclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import vampdev.vampclient.systems.commands.Command;
import vampdev.vampclient.systems.commands.arguments.ProfileArgumentType;
import vampdev.vampclient.systems.profiles.Profile;
import vampdev.vampclient.systems.profiles.Profiles;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class ProfilesCommand extends Command {

    public ProfilesCommand() {
        super("profiles", "Loads and saves profiles.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("profile", ProfileArgumentType.profile())
                .then(literal("load").executes(context -> {
                    Profile profile = ProfileArgumentType.getProfile(context, "profile");

                    if (profile != null) {
                        profile.load();
                        info("Loaded profile (highlight)%s(default).", profile.name);
                    }

                    return SINGLE_SUCCESS;
                }))
                .then(literal("save").executes(context -> {
                    Profile profile = ProfileArgumentType.getProfile(context, "profile");

                    if (profile != null) {
                        profile.save();
                        info("Saved profile (highlight)%s(default).", profile.name);
                    }

                    return SINGLE_SUCCESS;
                }))
                .then(literal("delete").executes(context -> {
                    Profile profile = ProfileArgumentType.getProfile(context, "profile");

                    if (profile != null) {
                        Profiles.get().remove(profile);
                        info("Deleted profile (highlight)%s(default).", profile.name);
                    }

                    return SINGLE_SUCCESS;
                })));
    }
}
