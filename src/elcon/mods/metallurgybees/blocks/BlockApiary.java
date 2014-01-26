package elcon.mods.metallurgybees.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import elcon.mods.metallurgybees.MetallurgyBees;
import forestry.core.gadgets.TileBase;

public class BlockApiary extends BlockContainer {

	public BlockApiary(int par1) {
		super(par1, MetallurgyBees.materialBeeHive);
	}

	@Override
	public TileEntity createNewTileEntity(World arg0) {
		Object apiaryClass = null;
		try {
			Class apiary = Class.forName("forestry.apiculture.gadgets.TileApiary");
			apiaryClass = apiary.newInstance();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return (TileEntity) apiaryClass;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
		TileBase tile = (TileBase) world.getBlockTileEntity(x, y, z);
		if(!tile.isUseableByPlayer(player)) {
			return false;
		}
		if(tile.allowsInteraction(player))
			tile.openGui(player, tile);
		return true;
	}

}
