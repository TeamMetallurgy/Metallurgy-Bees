package elcon.mods.metallurgybees.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.metallurgybees.MetallurgyBees;
import elcon.mods.metallurgybees.types.MetallurgyFrameTypes;
import elcon.mods.metallurgybees.util.MBUtil;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IHiveFrame;

public class ItemHiveFrame extends Item implements IHiveFrame {

	public MetallurgyFrameTypes types;

	public ItemHiveFrame(MetallurgyFrameTypes frameType) {
		this.types = frameType;
		setMaxStackSize(1);
		setMaxDamage(frameType.maxDamage);
		setCreativeTab(MetallurgyBees.creativeTab);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return StatCollector.translateToLocal("metallurgy.frames." + types.name().toLowerCase()) + " " + StatCollector.translateToLocal(getUnlocalizedName());
	}
	
	@Override
	public String getUnlocalizedName() {
		return "item.metallurgyFrame.name";
	}

	@Override
	public ItemStack frameUsed(IBeeHousing housing, ItemStack frame, IBee queen, int wear) {
		frame.setItemDamage(frame.getItemDamage() + wear);

		if (frame.getItemDamage() >= frame.getMaxDamage()) {
 			// Break the frame.
 			frame = null;
 		}

		return frame;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon("metallurgybees:frame" + MBUtil.firstUpperCase(types.name().toLowerCase()));
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add("Mutation Modifier:" + types.getMutationModifier(null, null, 0f));
		par3List.add("Flowering Modifier:" + types.getFloweringModifier(null, 0f));
		par3List.add("Production Modifier:" + types.getProductionModifier(null, 0f));
		par3List.add("Territory Modifier:" + types.getTerritoryModifier(null, 0f));
		par3List.add("LifeSpan Modifier:" + types.getLifespanModifier(null, null, 0f));
	}

	@Override
	public IBeeModifier getBeeModifier() {
		return types;
	}
}
