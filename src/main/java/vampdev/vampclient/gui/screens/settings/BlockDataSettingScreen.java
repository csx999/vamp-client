

package vampdev.vampclient.gui.screens.settings;

import vampdev.vampclient.gui.GuiTheme;
import vampdev.vampclient.gui.WindowScreen;
import vampdev.vampclient.gui.renderer.GuiRenderer;
import vampdev.vampclient.gui.widgets.containers.WTable;
import vampdev.vampclient.gui.widgets.input.WTextBox;
import vampdev.vampclient.gui.widgets.pressable.WButton;
import vampdev.vampclient.settings.BlockDataSetting;
import vampdev.vampclient.settings.IBlockData;
import vampdev.vampclient.utils.misc.IChangeable;
import vampdev.vampclient.utils.misc.ICopyable;
import vampdev.vampclient.utils.misc.ISerializable;
import vampdev.vampclient.utils.misc.Names;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.StringUtils;
import vampdev.vampclient.VampClient;

import java.util.ArrayList;
import java.util.List;

public class BlockDataSettingScreen extends WindowScreen {
    private static final List<Block> BLOCKS = new ArrayList<>(100);

    private final BlockDataSetting<?> setting;

    private WTable table;
    private String filterText = "";

    public BlockDataSettingScreen(GuiTheme theme, BlockDataSetting<?> setting) {
        super(theme, "Configure Blocks");

        this.setting = setting;
    }

    @Override
    public void initWidgets() {
        WTextBox filter = add(theme.textBox("")).minWidth(400).expandX().widget();
        filter.setFocused(true);
        filter.action = () -> {
            filterText = filter.get().trim();

            table.clear();
            initTable();
        };

        table = add(theme.table()).expandX().widget();

        initTable();
    }

    public <T extends ICopyable<T> & ISerializable<T> & IChangeable & IBlockData<T>> void initTable() {
        for (Block block : Registry.BLOCK) {
            T blockData = (T) setting.get().get(block);

            if (blockData != null && blockData.isChanged()) BLOCKS.add(0, block);
            else BLOCKS.add(block);
        }

        for (Block block : BLOCKS) {
            String name = Names.get(block);
            if (!StringUtils.containsIgnoreCase(name, filterText)) continue;

            T blockData = (T) setting.get().get(block);

            table.add(theme.itemWithLabel(block.asItem().getDefaultStack(), Names.get(block))).expandCellX();
            table.add(theme.label((blockData != null && blockData.isChanged()) ? "*" : " "));

            WButton edit = table.add(theme.button(GuiRenderer.EDIT)).widget();
            edit.action = () -> {
                T data = blockData;
                if (data == null) data = (T) setting.defaultData.get().copy();

                VampClient.mc.setScreen(data.createScreen(theme, block, (BlockDataSetting<T>) setting));
            };

            WButton reset = table.add(theme.button(GuiRenderer.RESET)).widget();
            reset.action = () -> {
                setting.get().remove(block);
                setting.onChanged();

                if (blockData != null && blockData.isChanged()) {
                    table.clear();
                    initTable();
                }
            };

            table.row();
        }

        BLOCKS.clear();
    }
}
