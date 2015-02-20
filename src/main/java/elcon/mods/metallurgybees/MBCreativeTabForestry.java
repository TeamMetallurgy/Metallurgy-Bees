package elcon.mods.metallurgybees;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MBCreativeTabForestry extends CreativeTabs {

	public MBCreativeTabForestry(String label) {
		super(label);
	}
	
	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(MetallurgyBees.honeyComb, 1, 0);
	}
	
	@Override
	public Item getTabIconItem() {
		return MetallurgyBees.honeyComb;
	}
	
}
