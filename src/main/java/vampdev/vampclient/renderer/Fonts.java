

package vampdev.vampclient.renderer;

import vampdev.vampclient.VampClient;
import vampdev.vampclient.gui.WidgetScreen;
import vampdev.vampclient.renderer.text.CustomTextRenderer;
import vampdev.vampclient.systems.config.Config;
import vampdev.vampclient.utils.Init;
import vampdev.vampclient.utils.InitStage;
import vampdev.vampclient.utils.files.StreamUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static vampdev.vampclient.VampClient.mc;

public class Fonts {
    private static final String[] BUILTIN_FONTS = { "JetBrains Mono.ttf", "Comfortaa.ttf", "Tw Cen MT.ttf", "Pixelation.ttf" };
    public static final String DEFAULT_FONT = "JetBrains Mono";
    private static final File FOLDER = new File(VampClient.FOLDER, "fonts");

    public static CustomTextRenderer CUSTOM_FONT;

    private static String lastFont = "";

    @Init(stage = InitStage.Pre)
    public static void init() {
        FOLDER.mkdirs();

        // Copy built in fonts if they not exist
        for (String font : BUILTIN_FONTS) {
            File file = new File(FOLDER, font);
            if (!file.exists()) {
                StreamUtils.copy(Fonts.class.getResourceAsStream("/assets/vamp/fonts/" + font), file);
            }
        }

        // Load default font
        CUSTOM_FONT = new CustomTextRenderer(new File(FOLDER, DEFAULT_FONT + ".ttf"));
        lastFont = DEFAULT_FONT;
    }

    @Init(stage = InitStage.Post)
    public static void load() {
        if (lastFont.equals(Config.get().font)) return;

        File file = new File(FOLDER, Config.get().font + ".ttf");
        if (!file.exists()) {
            Config.get().font = DEFAULT_FONT;
            file = new File(FOLDER, Config.get().font + ".ttf");
        }

        try {
            CUSTOM_FONT = new CustomTextRenderer(file);
        } catch (Exception ignored) {
            Config.get().font = DEFAULT_FONT;
            file = new File(FOLDER, Config.get().font + ".ttf");

            CUSTOM_FONT = new CustomTextRenderer(file);
        }

        if (mc.currentScreen instanceof WidgetScreen && Config.get().customFont) {
            ((WidgetScreen) mc.currentScreen).invalidate();
        }

        lastFont = Config.get().font;
    }

    public static String[] getAvailableFonts() {
        List<String> fonts = new ArrayList<>(4);

        File[] files = FOLDER.listFiles(File::isFile);
        if (files != null) {
            for (File file : files) {
                int i = file.getName().lastIndexOf('.');
                if (file.getName().substring(i).equals(".ttf")) {
                    fonts.add(file.getName().substring(0, i));
                }
            }
        }

        return fonts.toArray(new String[0]);
    }
}
