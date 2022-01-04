

package vampdev.vampclient.gui.tabs.builtin;

import vampdev.vampclient.events.meteor.KeyEvent;
import vampdev.vampclient.events.meteor.MouseButtonEvent;
import vampdev.vampclient.gui.GuiTheme;
import vampdev.vampclient.gui.WindowScreen;
import vampdev.vampclient.gui.renderer.GuiRenderer;
import vampdev.vampclient.gui.tabs.Tab;
import vampdev.vampclient.gui.tabs.TabScreen;
import vampdev.vampclient.gui.tabs.WindowTabScreen;
import vampdev.vampclient.gui.widgets.WKeybind;
import vampdev.vampclient.gui.widgets.containers.WTable;
import vampdev.vampclient.gui.widgets.input.WTextBox;
import vampdev.vampclient.gui.widgets.pressable.WButton;
import vampdev.vampclient.gui.widgets.pressable.WMinus;
import vampdev.vampclient.gui.widgets.pressable.WPlus;
import vampdev.vampclient.systems.macros.Macro;
import vampdev.vampclient.systems.macros.Macros;
import vampdev.vampclient.utils.misc.NbtUtils;
import vampdev.vampclient.eventbus.EventHandler;
import vampdev.vampclient.eventbus.EventPriority;
import net.minecraft.client.gui.screen.Screen;
import vampdev.vampclient.VampClient;

public class MacrosTab extends Tab {
    public MacrosTab() {
        super("Macros");
    }

    @Override
    public TabScreen createScreen(GuiTheme theme) {
        return new MacrosScreen(theme, this);
    }

    @Override
    public boolean isScreen(Screen screen) {
        return screen instanceof MacrosScreen;
    }

    private static class MacrosScreen extends WindowTabScreen {
        public MacrosScreen(GuiTheme theme, Tab tab) {
            super(theme, tab);
        }

        @Override
        public void initWidgets() {
            // Macros
            if (Macros.get().getAll().size() > 0) {
                WTable table = add(theme.table()).expandX().widget();

                for (Macro macro : Macros.get()) {
                    table.add(theme.label(macro.name + " (" + macro.keybind + ")"));

                    WButton edit = table.add(theme.button(GuiRenderer.EDIT)).expandCellX().right().widget();
                    edit.action = () -> VampClient.mc.setScreen(new MacroEditorScreen(theme, macro));

                    WMinus remove = table.add(theme.minus()).widget();
                    remove.action = () -> {
                        Macros.get().remove(macro);

                        clear();
                        initWidgets();
                    };

                    table.row();
                }
            }

            // New
            WButton create = add(theme.button("Create")).expandX().widget();
            create.action = () -> VampClient.mc.setScreen(new MacroEditorScreen(theme, null));
        }

        @Override
        public boolean toClipboard() {
            return NbtUtils.toClipboard(Macros.get());
        }

        @Override
        public boolean fromClipboard() {
            return NbtUtils.fromClipboard(Macros.get());
        }
    }

    private static class MacroEditorScreen extends WindowScreen {
        private final boolean isNew;

        private final Macro macro;

        private WKeybind keybind;
        private boolean binding;

        public MacroEditorScreen(GuiTheme theme, Macro m) {
            super(theme, m == null ? "Create Macro" : "Edit Macro");

            isNew = m == null;
            macro = isNew ? new Macro() : m;
        }

        @Override
        public void initWidgets() {
            initWidgets(macro);
        }

        private void initWidgets(Macro m) {
            // Name
            WTable t = add(theme.table()).widget();

            t.add(theme.label("Name:"));
            WTextBox name = t.add(theme.textBox(m == null ? "" : macro.name)).minWidth(400).expandX().widget();
            name.setFocused(true);
            name.action = () -> macro.name = name.get().trim();
            t.row();

            // Messages
            t.add(theme.label("Messages:")).padTop(4).top();
            WTable lines = t.add(theme.table()).widget();
            initTable(lines);

            // Bind
            keybind = add(theme.keybind(macro.keybind)).expandX().widget();
            keybind.actionOnSet = () -> binding = true;

            // Apply
            WButton apply = add(theme.button(isNew ? "Add" : "Apply")).expandX().widget();
            apply.action = () -> {
                if (isNew) {
                    if (macro.name != null && !macro.name.isEmpty() && macro.messages.size() > 0 && macro.keybind.isSet()) {
                        Macros.get().add(macro);
                        onClose();
                    }
                } else {
                    Macros.get().save();
                    onClose();
                }
            };

            enterAction = apply.action;
        }

        private void initTable(WTable lines) {
            if (macro.messages.isEmpty()) macro.addMessage("");

            for (int i = 0; i < macro.messages.size(); i++) {
                int ii = i;

                WTextBox line = lines.add(theme.textBox(macro.messages.get(i))).minWidth(400).expandX().widget();
                line.action = () -> macro.messages.set(ii, line.get().trim());

                if (i != macro.messages.size() - 1) {
                    WMinus remove = lines.add(theme.minus()).widget();
                    remove.action = () -> {
                        macro.removeMessage(ii);

                        clear();
                        initWidgets(macro);
                    };
                } else {
                    WPlus add = lines.add(theme.plus()).widget();
                    add.action = () -> {
                        macro.addMessage("");

                        clear();
                        initWidgets(macro);
                    };
                }

                lines.row();
            }
        }

        @EventHandler(priority = EventPriority.HIGHEST)
        private void onKey(KeyEvent event) {
            if (onAction(true, event.key)) event.cancel();
        }

        @EventHandler(priority = EventPriority.HIGHEST)
        private void onButton(MouseButtonEvent event) {
            if (onAction(false, event.button)) event.cancel();
        }

        private boolean onAction(boolean isKey, int value) {
            if (binding) {
                keybind.onAction(isKey, value);

                binding = false;
                return true;
            }

            return false;
        }
    }
}
