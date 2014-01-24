package elcon.mods.metallurgybees.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import rebelkeithy.mods.metallurgy.api.IOreInfo;
import cpw.mods.fml.common.IWorldGenerator;
import elcon.mods.metallurgybees.MetallurgyBees;
import elcon.mods.metallurgybees.Metals;
import elcon.mods.metallurgybees.tileentities.TileEntityMetadata;
import elcon.mods.metallurgybees.types.MetallurgyBeeTypes;

public class WorldGenBeehives extends WorldGenerator implements IWorldGenerator {

	public MetallurgyBeeTypes beeType;

	public WorldGenBeehives(MetallurgyBeeTypes beeType) {
		this.beeType = beeType;
	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		BiomeGenBase b = world.getBiomeGenForCoords(chunkX, chunkZ);
		for(int i = 0; i < 25; i++) {
			int x = chunkX * 16 + rand.nextInt(16);
			int y = 20 + rand.nextInt(50);
			int z = chunkZ * 16 + rand.nextInt(16);
			generate(world, rand, x, y, z);
		}
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		Block block = Block.blocksList[world.getBlockId(x, y, z)];
		if(block != null) {
			IOreInfo info = Metals.getMetal(beeType.name).oreInfo;
			if(info != null) {
				ItemStack ore = info.getOre();
				if(ore != null) {
					int id = ore.itemID;
					if(block.isGenMineableReplaceable(world, x, y, z, id)) {
						if(world.getBlockMetadata(x, y, z) == Metals.getMetal(beeType.name).oreInfo.getOre().getItemDamage()) {
							world.setBlock(x, y, z, MetallurgyBees.beehive.blockID, 1, 0);
							((TileEntityMetadata) world.getBlockTileEntity(x, y, z)).setTileMetadata(beeType.ordinal());
							return true;
						}
					}
				}
			}
		}
		return false;
	}

}
