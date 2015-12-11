package elcon.mods.metallurgybees.blocks;

import java.util.HashMap;

import com.teammetallurgy.metallurgy.api.MetallurgyApi;

import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.metallurgybees.items.ItemBlockExtendedMetadata;
import net.minecraft.block.Block;

public class MBBlocks {
	
	public static HashMap<String, Block> beehives = new HashMap<String, Block>();
	
	public static void initBlocks(){
		String[] setNames = MetallurgyApi.getSetNames();
		for (String setName : setNames) {

			String blockSetName = setName.substring(0, 1).toUpperCase() + setName.substring(1).toLowerCase();
			Block beehive = new BlockBeehive().setSetName(setName).setBlockName("metallurgyBeehive" + blockSetName);

			GameRegistry.registerBlock(beehive, ItemBlockExtendedMetadata.class, "metallurgyBeehive" + blockSetName);

			beehives.put(setName, beehive);
		}
	}
}
