package elcon.mods.metallurgybees.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.metallurgybees.LocalizationHelper;

public class ItemBeeGun extends ItemBow {

	public ItemBeeGun(int par1) {
		super(par1);
		setMaxDamage(200);
	}
	
	@Override
	public String getItemDisplayName(ItemStack stack) {
		return LocalizationHelper.localize("item.metallurgyBeegun.name");
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("metallurgybees:miscBeeGun");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getItemIconForUseDuration(int par1) {
		return itemIcon;
	}
}
