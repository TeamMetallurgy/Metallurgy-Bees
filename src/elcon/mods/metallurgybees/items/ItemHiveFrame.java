package elcon.mods.metallurgybees.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.metallurgybees.LocalizationHelper;
import elcon.mods.metallurgybees.MetallurgyBees;
import elcon.mods.metallurgybees.types.MetallurgyFrameTypes;
import elcon.mods.metallurgybees.util.MBUtil;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IHiveFrame;

public class ItemHiveFrame extends Item implements IHiveFrame {

	public String name;
	private Icon[] frameIcon;
	
	public ItemHiveFrame(int par1) {
		super(par1);
		setMaxStackSize(1);
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(MetallurgyBees.creativeTab);
	}

	@Override
	public String getItemDisplayName(ItemStack stack) {
		return LocalizationHelper.localize("metallurgy.frames." + MetallurgyFrameTypes.values()[stack.getItemDamage()].name().toLowerCase()) + " " + LocalizationHelper.localize(getUnlocalizedName());
	}

	@Override
	public String getUnlocalizedName() {
		return "item.metallurgyFrame.name";
	}

	@Override
	public float getFloweringModifier(IBeeGenome beeGenome, float currentModifier) {
		MetallurgyFrameTypes frame = MetallurgyFrameTypes.valueOf(name);
		return frame.floweringModifer;
	}

	@Override
	public float getGeneticDecay(IBeeGenome beeGenome, float currentModifier) {
		return 1.0F;
	}

	@Override
	public float getLifespanModifier(IBeeGenome beeGenome1, IBeeGenome beeGenome2, float currentModifier) {
		MetallurgyFrameTypes frame = MetallurgyFrameTypes.valueOf(name);
		return frame.lifespanModifer;
	}

	@Override
	public float getMutationModifier(IBeeGenome beeGenome1, IBeeGenome beeGenome2, float currentModifier) {
		MetallurgyFrameTypes frame = MetallurgyFrameTypes.valueOf(name);
		return frame.mutationModifier;
	}

	@Override
	public float getProductionModifier(IBeeGenome beeGenome, float currentModifier) {
		MetallurgyFrameTypes frame = MetallurgyFrameTypes.valueOf(name);
		return currentModifier < 16.0F ? frame.productionModifer : 1.0F;
	}

	@Override
	public float getTerritoryModifier(IBeeGenome beeGenome, float currentModifier) {
		MetallurgyFrameTypes frame = MetallurgyFrameTypes.valueOf(name);
		return frame.territoryModifer;
	}

	@Override
	public boolean isHellish() {
		return false;
	}

	@Override
	public boolean isSealed() {
		return false;
	}

	@Override
	public boolean isSelfLighted() {
		return false;
	}

	@Override
	public boolean isSunlightSimulated() {
		return false;
	}

	@Override
	public ItemStack frameUsed(IBeeHousing housing, ItemStack frame, IBee queen, int wear) {
		if(getMaxDamage() == 0) {
			setMaxDamage(MetallurgyFrameTypes.values()[frame.getItemDamage()].maxDamage);
		}
		frame.setItemDamage(frame.getItemDamage() + wear);
		if(frame.getItemDamage() >= frame.getMaxDamage()) {
			return null;
		}
		return frame;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.frameIcon = new Icon[MetallurgyFrameTypes.values().length];
		for(int i = 0; i < MetallurgyFrameTypes.values().length; i++) {
			MetallurgyFrameTypes hiveFrames = MetallurgyFrameTypes.values()[i];
			this.frameIcon[i] = iconRegister.registerIcon("metallurgybees:frame" + MBUtil.firstUpperCase(hiveFrames.name().toLowerCase()));
		}
	}
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add("Mutation Modifier:" + MetallurgyFrameTypes.values()[par1ItemStack.getItemDamage()].mutationModifier);
		par3List.add("Flowering Modifier:" + MetallurgyFrameTypes.values()[par1ItemStack.getItemDamage()].floweringModifer);
		par3List.add("Production Modifier:" + MetallurgyFrameTypes.values()[par1ItemStack.getItemDamage()].productionModifer);
		par3List.add("Territory Modifier:" + MetallurgyFrameTypes.values()[par1ItemStack.getItemDamage()].territoryModifer);
	}
	@Override
	public Icon getIconFromDamage(int par1) {
		return this.frameIcon[par1];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < MetallurgyFrameTypes.values().length; i++) {
			list.add(new ItemStack(id, 1, i));
			name = MetallurgyFrameTypes.values()[i].name();
		}
	}
}
