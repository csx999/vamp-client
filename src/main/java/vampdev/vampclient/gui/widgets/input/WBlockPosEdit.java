

package vampdev.vampclient.gui.widgets.input;

import vampdev.vampclient.VampClient;
import vampdev.vampclient.events.entity.player.InteractBlockEvent;
import vampdev.vampclient.events.entity.player.StartBreakingBlockEvent;
import vampdev.vampclient.gui.widgets.containers.WHorizontalList;
import vampdev.vampclient.gui.widgets.pressable.WButton;
import vampdev.vampclient.systems.modules.Modules;
import vampdev.vampclient.systems.modules.render.marker.Marker;
import vampdev.vampclient.eventbus.EventHandler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import vampdev.vampclient.utils.Utils;

public class WBlockPosEdit extends WHorizontalList {
    public Runnable action;
    public Runnable actionOnRelease;

    private WTextBox textBoxX, textBoxY, textBoxZ;

    private Screen previousScreen;

    private BlockPos value;
    private BlockPos lastValue;

    private boolean clicking;

    public WBlockPosEdit(BlockPos value) {
        this.value = value;
    }

    @Override
    public void init() {
        addTextBox();

        if (Utils.canUpdate()) {
            WButton click = add(theme.button("Click")).expandX().widget();
            click.action = () -> {
                String sb = "Click!\n" + "Right click to pick a new position.\n" +
                    "Left click to cancel.";
                Modules.get().get(Marker.class).info(sb);

                clicking = true;
                VampClient.EVENT_BUS.subscribe(this);
                previousScreen = VampClient.mc.currentScreen;
                VampClient.mc.setScreen(null);
            };

            WButton here = add(theme.button("Set Here")).expandX().widget();
            here.action = () -> {
                lastValue = value;
                set(new BlockPos(VampClient.mc.player.getBlockPos()));
                newValueCheck();

                clear();
                init();
            };
        }
    }

    @EventHandler
    private void onStartBreakingBlock(StartBreakingBlockEvent event) {
        if (clicking) {
            clicking = false;
            event.cancel();
            VampClient.EVENT_BUS.unsubscribe(this);
            VampClient.mc.setScreen(previousScreen);
        }
    }

    @EventHandler
    private void onInteractBlock(InteractBlockEvent event) {
        if (clicking) {
            if (event.result.getType() == HitResult.Type.MISS) return;
            lastValue = value;
            set(event.result.getBlockPos());
            newValueCheck();

            clicking = false;
            event.cancel();
            VampClient.EVENT_BUS.unsubscribe(this);
            VampClient.mc.setScreen(previousScreen);
        }
    }

    private boolean filter(String text, char c) {
        boolean good;
        boolean validate = true;

        if (c == '-' && text.isEmpty()) {
            good = true;
            validate = false;
        }
        else good = Character.isDigit(c);

        if (good && validate) {
            try {
                Integer.parseInt(text + c);
            } catch (NumberFormatException ignored) {
                good = false;
            }
        }

        return good;
    }

    public BlockPos get() {
        return value;
    }

    public void set(BlockPos value) {
        this.value = value;
    }

    private void addTextBox() {
        textBoxX = add(theme.textBox(Integer.toString(value.getX()), this::filter)).minWidth(75).widget();
        textBoxY = add(theme.textBox(Integer.toString(value.getY()), this::filter)).minWidth(75).widget();
        textBoxZ = add(theme.textBox(Integer.toString(value.getZ()), this::filter)).minWidth(75).widget();

        textBoxX.actionOnUnfocused = () -> {
            lastValue = value;
            if (textBoxX.get().isEmpty()) set(new BlockPos(0, 0, 0));
            else {
                set(new BlockPos(Integer.parseInt(textBoxX.get()), value.getY(), value.getZ()));
            }
            newValueCheck();
        };

        textBoxY.actionOnUnfocused = () -> {
            lastValue = value;
            if (textBoxY.get().isEmpty()) set(new BlockPos(0, 0, 0));
            else {
                set(new BlockPos(value.getX(), Integer.parseInt(textBoxY.get()), value.getZ()));
            }
            newValueCheck();
        };

        textBoxZ.actionOnUnfocused = () -> {
            lastValue = value;
            if (textBoxZ.get().isEmpty()) set(new BlockPos(0, 0, 0));
            else {
                set(new BlockPos(value.getX(), value.getY(), Integer.parseInt(textBoxZ.get())));
            }
            newValueCheck();
        };
    }

    private void newValueCheck() {
        if (value != lastValue) {
            if (action != null) action.run();
            if (actionOnRelease != null) actionOnRelease.run();
        }
    }
}
