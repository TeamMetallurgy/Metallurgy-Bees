package elcon.mods.metallurgybees.blocks;

import java.util.ArrayList;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import elcon.mods.metallurgybees.tileentities.TileEntityExtended;

public abstract class BlockExtendedContainer extends BlockContainer {

	public BlockExtendedContainer(Material material) {
		super(material);
	}
	
	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z) {
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		TileEntityExtended tile = (TileEntityExtended) world.getTileEntity(x, y, z);
		if(tile != null && !tile.hasDropppedBlock) {
			drops = world.getBlock(x, y, z).getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), EnchantmentHelper.getFortuneModifier(player));
		}
		boolean hasBeenBroken = world.setBlockToAir(x, y, z);
		if(hasBeenBroken && !world.isRemote && drops.size() > 0 && (player == null || !player.capabilities.isCreativeMode)) {
			for(ItemStack drop : drops) {
				dropBlockAsItem(world, x, y, z, drop);
			}
			tile.hasDropppedBlock = true;
		}
		return hasBeenBroken;
	}
	
	@Override
	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float chance, int fortune) {
		TileEntityExtended tile = (TileEntityExtended) world.getTileEntity(x, y, z);
		if(tile != null && !tile.hasDropppedBlock) {
			super.dropBlockAsItemWithChance(world, x, y, z, meta, chance, fortune);
		}
	}
	
	public TileEntity getTileEntity(IBlockAccess blockAccess, int x, int y, int z) {
		TileEntity tile = (TileEntity) blockAccess.getTileEntity(x, y, z);
		if(tile == null || !tile.getClass().isAssignableFrom(getTileEntityClass())) {
			tile = createNewTileEntity(null,0);
			if(blockAccess instanceof World) {
				tile = createNewTileEntity(((World) blockAccess),0);
				((World) blockAccess).setTileEntity(x, y, z, tile);
			}
		}
		return tile;
	}

	public abstract Class<?> getTileEntityClass();
}
