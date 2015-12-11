package elcon.mods.metallurgybees;

import elcon.mods.metallurgybees.items.MBItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MBCreativeTabForestry extends CreativeTabs {

	public MBCreativeTabForestry(String label) {
		super(label);
	}
	
	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(MBItems.honeyComb, 1, 0);
	}
	
	@Override
	public Item getTabIconItem() {
		return MBItems.honeyComb;
	}
	
}
