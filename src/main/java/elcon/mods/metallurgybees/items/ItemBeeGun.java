package elcon.mods.metallurgybees.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBow;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBeeGun extends ItemBow {

	public ItemBeeGun() {
		setMaxDamage(200);
	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("metallurgybees:miscBeeGun");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getItemIconForUseDuration(int par1) {
		return itemIcon;
	}
}
