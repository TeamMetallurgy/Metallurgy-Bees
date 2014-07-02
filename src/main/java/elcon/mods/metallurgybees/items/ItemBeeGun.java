package elcon.mods.metallurgybees.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBow;
import net.minecraft.util.IIcon;

public class ItemBeeGun extends ItemBow {

	public ItemBeeGun() {
		super();
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
