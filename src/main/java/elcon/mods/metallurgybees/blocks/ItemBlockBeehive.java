package elcon.mods.metallurgybees.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemBlockBeehive extends ItemBlock {

    public ItemBlockBeehive(Block block) {
        super(block);
    }

    @Override
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
        return StatCollector.translateToLocal("metallurgy.metals." + ((BlockBeehive) Block.getBlockFromItem(par1ItemStack.getItem())).beeName.replace(" ", "")) + " " + StatCollector.translateToLocal("tile.metallurgyBeehive.name");
    }
}
