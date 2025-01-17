

package vampdev.vampclient.utils.misc;

import vampdev.vampclient.systems.config.Config;
import vampdev.vampclient.utils.Utils;
import net.minecraft.SharedConstants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static vampdev.vampclient.VampClient.mc;

// TODO: Replace with starscript everywhere
public class Placeholders {
    private static final Pattern pattern = Pattern.compile("(\\{version}|\\{mc_version}|\\{player}|\\{username}|\\{server})");

    public static String apply(String string) {
        Matcher matcher = pattern.matcher(string);
        StringBuilder sb = new StringBuilder(string.length());

        while (matcher.find()) {
            matcher.appendReplacement(sb, getReplacement(matcher.group(1)));
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    private static String getReplacement(String placeholder) {
        return switch (placeholder) {
            case "{version}" -> Config.get().version != null ? (Config.get().devBuild.isEmpty() ? Config.get().version.toString() : Config.get().version + " " + Config.get().devBuild) : "";
            case "{mc_version}" -> SharedConstants.getGameVersion().getName();
            case "{player}", "{username}" -> mc.getSession().getUsername();
            case "{server}" -> Utils.getWorldName();
            default -> "";
        };
    }
}
