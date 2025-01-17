

package vampdev.vampclient.gui.tabs.builtin;

import vampdev.vampclient.gui.GuiTheme;
import vampdev.vampclient.gui.tabs.Tab;
import vampdev.vampclient.gui.tabs.TabScreen;
import vampdev.vampclient.gui.tabs.WindowTabScreen;
import vampdev.vampclient.renderer.Fonts;
import vampdev.vampclient.systems.config.Config;
import vampdev.vampclient.utils.misc.NbtUtils;
import vampdev.vampclient.utils.render.color.RainbowColors;
import vampdev.vampclient.utils.render.prompts.YesNoPrompt;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import vampdev.vampclient.VampClient;
import vampdev.vampclient.settings.*;

public class ConfigTab extends Tab {
    private static final Settings settings = new Settings();

    private static final SettingGroup sgVisual = settings.createGroup("Visual");
    private static final SettingGroup sgChat = settings.createGroup("Chat");
    private static final SettingGroup sgMisc = settings.createGroup("Misc");

    // Visual

    public static final Setting<Boolean> customFont = sgVisual.add(new BoolSetting.Builder()
        .name("custom-font")
        .description("Use a custom font.")
        .defaultValue(true)
        .onChanged(aBoolean -> Config.get().customFont = aBoolean)
        .onModuleActivated(booleanSetting -> booleanSetting.set(Config.get().customFont))
        .build()
    );

    public static final Setting<String> font = sgVisual.add(new ProvidedStringSetting.Builder()
        .name("font")
        .description("Custom font to use (picked from .minecraft/vamp/fonts folder).")
        .supplier(Fonts::getAvailableFonts)
        .defaultValue(Fonts.DEFAULT_FONT)
        .onChanged(s -> {
            Config.get().font = s;
            Fonts.load();
        })
        .onModuleActivated(stringSetting -> stringSetting.set(Config.get().font))
        .visible(customFont::get)
        .build()
    );

    public static final Setting<Double> rainbowSpeed = sgVisual.add(new DoubleSetting.Builder()
        .name("rainbow-speed")
        .description("The global rainbow speed.")
        .defaultValue(0.5)
        .range(0, 10)
        .sliderMax(5)
        .onChanged(value -> RainbowColors.GLOBAL.setSpeed(value / 100))
        .onModuleActivated(doubleSetting -> doubleSetting.set(Config.get().rainbowSpeed))
        .build()
    );

    public static final Setting<Boolean> titleScreenCredits = sgVisual.add(new BoolSetting.Builder()
        .name("title-screen-credits")
        .description("Show Meteor credits on title screen")
        .defaultValue(true)
        .onChanged(aBool -> Config.get().titleScreenCredits = aBool)
        .onModuleActivated(boolSetting -> boolSetting.set(Config.get().titleScreenCredits))
        .build()
    );

    public static final Setting<Boolean> titleScreenSplashes = sgVisual.add(new BoolSetting.Builder()
        .name("title-screen-splashes")
        .description("Show Meteor splash texts on title screen")
        .defaultValue(true)
        .onChanged(aBool -> Config.get().titleScreenSplashes = aBool)
        .onModuleActivated(boolSetting -> boolSetting.set(Config.get().titleScreenSplashes))
        .build()
    );

    public static final Setting<Boolean> customWindowTitle = sgVisual.add(new BoolSetting.Builder()
        .name("custom-window-title")
        .description("Show custom text in the window title.")
        .defaultValue(false)
        .onChanged(aBool -> Config.get().customWindowTitle = aBool)
        .onModuleActivated(boolSetting -> boolSetting.set(Config.get().customWindowTitle))
        .build()
    );

    public static final Setting<String> customWindowTitleText = sgVisual.add(new StringSetting.Builder()
        .name("window-title-text")
        .description("The text it displays in the window title.")
        .defaultValue("Minecraft {mc_version} - Vamp Client {version}")
        .onChanged(titleText -> Config.get().customWindowTitleText = titleText)
        .onModuleActivated(stringSetting -> stringSetting.set(Config.get().customWindowTitleText))
        .visible(customWindowTitle::get)
        .build()
    );

    // Chat

    public static final Setting<String> prefix = sgChat.add(new StringSetting.Builder()
        .name("prefix")
        .description("Prefix.")
        .defaultValue(".")
        .onChanged(s -> Config.get().prefix = s)
        .onModuleActivated(stringSetting -> stringSetting.set(Config.get().prefix))
        .build()
    );

    public static final Setting<Boolean> prefixOpensConsole = sgChat.add(new BoolSetting.Builder()
        .name("open-chat-on-prefix")
        .description("Open chat when command prefix is pressed. Works like pressing '/' in vanilla.")
        .defaultValue(true)
        .onChanged(aBoolean -> Config.get().prefixOpensConsole = aBoolean)
        .onModuleActivated(booleanSetting -> booleanSetting.set(Config.get().prefixOpensConsole))
        .build()
    );

    public static final Setting<Boolean> chatFeedback = sgChat.add(new BoolSetting.Builder()
        .name("chat-feedback")
        .description("Sends chat feedback when meteor performs certain actions.")
        .defaultValue(true)
        .onChanged(aBoolean -> Config.get().chatFeedback = aBoolean)
        .onModuleActivated(booleanSetting -> booleanSetting.set(Config.get().chatFeedback))
        .build()
    );

    public static final Setting<Boolean> deleteChatFeedback = sgChat.add(new BoolSetting.Builder()
        .name("delete-chat-feedback")
        .description("Delete previous matching chat feedback to keep chat clear.")
        .visible(chatFeedback::get)
        .defaultValue(true)
        .onChanged(aBoolean -> Config.get().deleteChatFeedback = aBoolean)
        .onModuleActivated(booleanSetting -> booleanSetting.set(Config.get().deleteChatFeedback))
        .build()
    );

    // Misc

    public static final Setting<Integer> rotationHoldTicks = sgMisc.add(new IntSetting.Builder()
        .name("rotation-hold")
        .description("Hold long to hold server side rotation when not sending any packets.")
        .defaultValue(4)
        .onChanged(integer -> Config.get().rotationHoldTicks = integer)
        .onModuleActivated(integerSetting -> integerSetting.set(Config.get().rotationHoldTicks))
        .build()
    );

    public static final Setting<Boolean> useTeamColor = sgMisc.add(new BoolSetting.Builder()
        .name("use-team-color")
        .description("Uses player's team color for rendering things like esp and tracers.")
        .defaultValue(true)
        .onChanged(aBoolean -> Config.get().useTeamColor = aBoolean)
        .onModuleActivated(booleanSetting -> booleanSetting.set(Config.get().useTeamColor))
        .build()
    );

    public static ConfigScreen currentScreen;

    public ConfigTab() {
        super("Config");
    }

    @Override
    public TabScreen createScreen(GuiTheme theme) {
        return currentScreen = new ConfigScreen(theme, this);
    }

    @Override
    public boolean isScreen(Screen screen) {
        return screen instanceof ConfigScreen;
    }

    public static class ConfigScreen extends WindowTabScreen {
        public ConfigScreen(GuiTheme theme, Tab tab) {
            super(theme, tab);

            settings.onActivated();

            onClosed(() -> {
                String prefix = Config.get().prefix;

                if (prefix.isBlank()) {
                    YesNoPrompt.create(theme, this.parent)
                        .title("Empty command prefix")
                        .message("You have set your command prefix to nothing.")
                        .message("This WILL prevent you from sending chat messages.")
                        .message("Do you want to reset your prefix back to '.'?")
                        .onYes(() -> Config.get().prefix = ".")
                        .id("empty-command-prefix")
                        .show();
                }
                else if (prefix.equals("/")) {
                    YesNoPrompt.create(theme, this.parent)
                        .title("Potential prefix conflict")
                        .message("You have set your command prefix to '/', which is used by minecraft.")
                        .message("This can cause conflict issues between meteor and minecraft commands.")
                        .message("Do you want to reset your prefix to '.'?")
                        .onYes(() -> Config.get().prefix = ".")
                        .id("minecraft-prefix-conflict")
                        .show();
                }
                else if (prefix.length() > 7) {
                    YesNoPrompt.create(theme, this.parent)
                        .title("Long command prefix")
                        .message("You have set your command prefix to a very long string.")
                        .message("This means that in order to execute any command, you will need to type %s followed by the command you want to run.", prefix)
                        .message("Do you want to reset your prefix back to '.'?")
                        .onYes(() -> Config.get().prefix = ".")
                        .id("long-command-prefix")
                        .show();
                }
                else if (isUsedKey()) {
                    YesNoPrompt.create(theme, this.parent)
                        .title("Prefix keybind")
                        .message("You have \"Open Chat On Prefix\" setting enabled and your command prefix has a conflict with another keybind.")
                        .message("Do you want to disable \"Open Chat On Prefix\" setting?")
                        .onYes(() -> Config.get().prefixOpensConsole = false)
                        .id("prefix-keybind")
                        .show();
                }
            });
        }

        @Override
        public void initWidgets() {
            add(theme.settings(settings)).expandX();
        }

        @Override
        public void tick() {
            super.tick();

            settings.tick(window, theme);
        }

        @Override
        public boolean toClipboard() {
            return NbtUtils.toClipboard(Config.get());
        }

        @Override
        public boolean fromClipboard() {
            return NbtUtils.fromClipboard(Config.get());
        }
    }

    private static boolean isUsedKey() {
        if (!Config.get().prefixOpensConsole) return false;

        String prefixKeybindTranslation = String.format("key.keyboard.%s",  Config.get().prefix.toLowerCase().substring(0,1));
        for (KeyBinding key: VampClient.mc.options.keysAll) {
            if (key.getBoundKeyTranslationKey().equals(prefixKeybindTranslation)) return true;
        }

        return false;
    }
}
