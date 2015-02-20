package elcon.mods.metallurgybees.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.metallurgybees.tileentities.TileEntityMetadata;
import elcon.mods.metallurgybees.util.MBUtilClient;

public class BlockExtendedMetadata extends BlockContainer {

	public BlockExtendedMetadata(Material material) {
		super(material);
	}

	public String getLocalizedName(ItemStack stack) {
		return StatCollector.translateToLocal(getUnlocalizedName(stack));
	}

	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityMetadata();
	}

	public int getPlacedMetadata(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int side, float xx, float yy, float zz) {
		return stack.getItemDamage();
	}

	public int getDroppedMetadata(World world, int x, int y, int z, int meta, int fortune) {
		return meta;
	}
	
	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z) {
		if(player.getCurrentEquippedItem() == null) {
			return super.getPlayerRelativeBlockHardness(player, world, x, y, z);
		}
		return player.getCurrentEquippedItem().getItem().getDigSpeed(player.getCurrentEquippedItem(), this, getMetadata(world, x, y, z)) / getBlockHardness(world, x, y, z) / 30F;
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z) {
		return breakBlock(this, player, world, x, y, z);
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int metadata, float chance, int fortune) {
		TileEntityMetadata tile = (TileEntityMetadata) world.getTileEntity(x, y, z);
		if(tile != null && !tile.droppedBlock) {
			super.dropBlockAsItemWithChance(world, x, y, z, tile.getTileMetadata(), chance, fortune);
		}
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		int count = quantityDropped(metadata, fortune, world.rand);
		for(int i = 0; i < count; i++) {
			Item item = getItemDropped(metadata, world.rand, fortune);
			if(item != null) {
				ret.add(new ItemStack(item, 1, getDroppedMetadata(world, x, y, z, metadata, fortune)));
			}
		}
		return ret;
	}

	public boolean breakBlock(BlockExtendedMetadata block, EntityPlayer player, World world, int x, int y, int z) {
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		Block block2 = (Block) block;
		TileEntityMetadata tile = (TileEntityMetadata) world.getTileEntity(x, y, z);
		if(tile != null && !tile.droppedBlock) {
			drops = block2.getDrops(world, x, y, z, getMetadata(world, x, y, z), EnchantmentHelper.getFortuneModifier(player));
		}
		boolean hasBeenBroken = world.setBlockToAir(x, y, z);
		if(hasBeenBroken && !world.isRemote && drops.size() > 0 && (player == null || !player.capabilities.isCreativeMode) && shouldDropItems(world, x, y, z, tile.getTileMetadata(), player, player.getCurrentEquippedItem())) {
			for(ItemStack drop : drops) {
				block.dropAsStack(world, x, y, z, drop);
			}
			tile.droppedBlock = true;
		}
		return hasBeenBroken;
	}

	public boolean shouldDropItems(World world, int x, int y, int z, int meta, EntityPlayer player, ItemStack stack) {
		return true;
	}

	@Override
	public int getDamageValue(World world, int x, int y, int z) {
		return getDroppedMetadata(world, x, y, z, getMetadata(world, x, y, z), 0);
	}

	public int getMetadata(IBlockAccess blockAccess, int x, int y, int z) {
		if(blockAccess.getBlock(x, y, z) instanceof BlockExtendedMetadata) {
			return ((TileEntityMetadata) blockAccess.getTileEntity(x, y, z)).getTileMetadata();
		}
		return blockAccess.getBlockMetadata(x, y, z);
	}

	public void setMetadata(World world, int x, int y, int z, int meta) {
		if(world.getBlock(x, y, z) instanceof BlockExtendedMetadata) {
			((TileEntityMetadata) world.getTileEntity(x, y, z)).setTileMetadata(meta);
		}
	}

	public void dropAsStack(World world, int x, int y, int z, ItemStack stack) {
		dropBlockAsItem(world, x, y, z, stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return getIcon(side, getMetadata(blockAccess, x, y, z));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer) {
		return MBUtilClient.addBlockDestroyEffects(world, x, y, z, meta, effectRenderer, this, getMetadata(world, x, y, z));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(World world, MovingObjectPosition target, EffectRenderer effectRenderer) {
		int x = target.blockX;
		int y = target.blockY;
		int z = target.blockZ;
		return MBUtilClient.addBlockHitEffects(world, target, effectRenderer, getMetadata(world, x, y, z));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
	}
}
