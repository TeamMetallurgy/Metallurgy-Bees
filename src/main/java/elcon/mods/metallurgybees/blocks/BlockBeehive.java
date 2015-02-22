package elcon.mods.metallurgybees.blocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.metallurgybees.MetallurgyBees;
import elcon.mods.metallurgybees.types.MetallurgyBeeTypes;
import elcon.mods.metallurgybees.util.MBUtil;
import forestry.api.apiculture.IHiveDrop;

public class BlockBeehive extends BlockExtendedMetadata {

	public BlockBeehive() {
		super(MetallurgyBees.materialBeeHive);
		setHardness(3.5F);
		setResistance(10.0F);
		setLightLevel(0.8F);
		setStepSound(Block.soundTypeStone);
		setCreativeTab(MetallurgyBees.creativeTab);
	}

	public String getLocalizedName(ItemStack stack) {
		return StatCollector.translateToLocal("metallurgy.metals." + MetallurgyBeeTypes.values()[stack.getItemDamage()].name) + " " + StatCollector.translateToLocal(getUnlocalizedName(stack));
	}

	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName();
	}

	@Override
	public String getUnlocalizedName() {
		return "tile.metallurgyBeehive.name";
	}

	@Override
	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
		if(entity instanceof EntityDragon || entity instanceof EntityWither) {
			return false;
		}
		return true;
	}

	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z) {
		int meta = getMetadata(world, x, y, z);
		float hardness = getBlockHardness(world, x, y, z);
		if(hardness < 0.0F) {
			return 0.0F;
		}
		if(!canHarvestBlock(player, meta)) {
			float speed = ForgeEventFactory.getBreakSpeed(player, this, meta, 1.0f, x, y, z);
			return (speed < 0 ? 0 : speed) / hardness / 100F;
		} else {
			return player.getBreakSpeed(this, false, meta, x, y, z) / hardness / 30F;
		}
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ArrayList<IHiveDrop> dropList = MetallurgyBeeTypes.values()[getMetadata(world, x, y, z)].hiveDrops;
		Collections.shuffle(dropList);
		int tries = 0;
		boolean hasPrincess = false;
		while((tries <= 10) && (!hasPrincess)) {
			tries++;
			for(IHiveDrop drop : dropList) {
				if(world.rand.nextInt(100) < drop.getChance(world, x, y, z)) {
					ret.add(drop.getPrincess(world, x, y, z, fortune));
					hasPrincess = true;
					break;
				}
			}
		}
		for(IHiveDrop drop : dropList) {
			if(world.rand.nextInt(100) < drop.getChance(world, x, y, z)) {
				ret.addAll(drop.getDrones(world, x, y, z, fortune));
				break;
			}
		}
		for(IHiveDrop drop : dropList) {
			if(world.rand.nextInt(100) < drop.getChance(world, x, y, z)) {
				ret.addAll(drop.getAdditional(world, x, y, z, fortune));
				break;
			}
		}
		return ret;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if(side == 0 || side == 1) {
			return MetallurgyBeeTypes.values()[meta].iconBeehiveTop;
		}
		return MetallurgyBeeTypes.values()[meta].iconBeehiveSide;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		for(int i = 0; i < MetallurgyBeeTypes.values().length; i++) {
			MetallurgyBeeTypes beeType = MetallurgyBeeTypes.values()[i];
			if(beeType.hasHive) {
				beeType.iconBeehiveSide = iconRegister.registerIcon("metallurgybees:beehive" + MBUtil.firstUpperCase(beeType.name) + "Side");
				beeType.iconBeehiveTop = iconRegister.registerIcon("metallurgybees:beehive" + MBUtil.firstUpperCase(beeType.name) + "Top");
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < MetallurgyBeeTypes.values().length; i++) {
			if(MetallurgyBeeTypes.values()[i].hasHive) {
				list.add(new ItemStack(item, 1, i));
			}
		}
	}
	
}
