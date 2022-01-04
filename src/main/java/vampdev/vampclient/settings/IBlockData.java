

package vampdev.vampclient.settings;

import vampdev.vampclient.gui.GuiTheme;
import vampdev.vampclient.gui.WidgetScreen;
import vampdev.vampclient.utils.misc.IChangeable;
import vampdev.vampclient.utils.misc.ICopyable;
import vampdev.vampclient.utils.misc.ISerializable;
import net.minecraft.block.Block;

public interface IBlockData<T extends ICopyable<T> & ISerializable<T> & IChangeable & IBlockData<T>> {
    WidgetScreen createScreen(GuiTheme theme, Block block, BlockDataSetting<T> setting);
}
