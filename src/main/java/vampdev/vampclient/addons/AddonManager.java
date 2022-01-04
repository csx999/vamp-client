

package vampdev.vampclient.addons;

import vampdev.vampclient.utils.Init;
import vampdev.vampclient.utils.InitStage;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.Person;

import java.util.ArrayList;
import java.util.List;

public class AddonManager {
    public static MeteorAddon METEOR;
    public static final List<MeteorAddon> ADDONS = new ArrayList<>();

    @Init(stage = InitStage.Pre)
    public static void init() {
        // Meteor pseudo addon
        {
            METEOR = new MeteorAddon() {
                @Override
                public void onInitialize() {}
            };

            ModMetadata metadata = FabricLoader.getInstance().getModContainer("vamp").get().getMetadata();

            METEOR.name = metadata.getName();
            METEOR.authors = new String[metadata.getAuthors().size()];
            if (metadata.containsCustomValue("vamp:color")) METEOR.color.parse(metadata.getCustomValue("vamp:color").getAsString());

            int i = 0;
            for (Person author : metadata.getAuthors()) {
                METEOR.authors[i++] = author.getName();
            }
        }

        // Addons
        for (EntrypointContainer<MeteorAddon> entrypoint : FabricLoader.getInstance().getEntrypointContainers("meteor", MeteorAddon.class)) {
            ModMetadata metadata = entrypoint.getProvider().getMetadata();
            MeteorAddon addon = entrypoint.getEntrypoint();

            addon.name = metadata.getName();
            addon.authors = new String[metadata.getAuthors().size()];
            if (metadata.containsCustomValue("vamp:color")) addon.color.parse(metadata.getCustomValue("vamp:color").getAsString());

            int i = 0;
            for (Person author : metadata.getAuthors()) {
                addon.authors[i++] = author.getName();
            }

            ADDONS.add(addon);
        }
    }
}
