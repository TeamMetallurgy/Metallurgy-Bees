package elcon.mods.metallurgybees.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.metallurgybees.blocks.BlockExtendedMetadata;
import elcon.mods.metallurgybees.tileentities.TileEntityMetadata;

public class ItemBlockExtendedMetadata extends ItemBlock {

	public ItemBlockExtendedMetadata(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return ((BlockExtendedMetadata) Block.getBlockFromItem(stack.getItem())).getLocalizedName(stack);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return ((BlockExtendedMetadata) Block.getBlockFromItem(stack.getItem())).getUnlocalizedName(stack);
	}
	
	@Override
	public String getUnlocalizedName() {
		// block
		return field_150939_a.getUnlocalizedName();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		return field_150939_a.getRenderColor(stack.getItemDamage());
	}
	
	@Override
	public int getMetadata(int meta) {
		return 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return field_150939_a.getIcon(2, meta);
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		Block block = field_150939_a;
		if(!(block instanceof BlockExtendedMetadata)) {
			return false;
		}
		int placedMeta = ((BlockExtendedMetadata) block).getPlacedMetadata(player, stack, world, x, y, z, side, hitX, hitY, hitZ);
		if(!world.setBlock(x, y, z, block, meta, 2)) {
			return false;
		}
		if(world.getBlock(x, y, z) == block) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile != null) {
				if(!(tile instanceof TileEntityMetadata)) {
					tile = new TileEntityMetadata();
					world.setTileEntity(x, y, z, tile);
				}
				((TileEntityMetadata) tile).setTileMetadata(placedMeta);
			}
			block.onBlockPlacedBy(world, x, y, z, player, stack);
			block.onPostBlockPlaced(world, x, y, z, meta);
		}
		return true;
	}
}
