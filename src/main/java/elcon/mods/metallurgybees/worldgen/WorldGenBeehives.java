package elcon.mods.metallurgybees.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import cpw.mods.fml.common.IWorldGenerator;
import elcon.mods.metallurgybees.MetallurgyBees;
import elcon.mods.metallurgybees.Metals;
import elcon.mods.metallurgybees.Metals.Metal;
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
		for(int i = 0; i < 30; i++) {
			int x = chunkX * 16 + rand.nextInt(16);
			int y = 5 + rand.nextInt(64);
			int z = chunkZ * 16 + rand.nextInt(16);
			generate(world, rand, x, y, z);
		}
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		if(block != null) {
			Metal metal = Metals.getMetal(beeType.name);
			if(metal.metalSet != null && metal.metalInfo != null) {
				Block ore = metal.metalSet.getDefaultOre();
				ItemStack oreStack = metal.metalSet.getOre(metal.metalInfo.getName());
				if(ore != null && oreStack != null) {
					if(block.isReplaceableOreGen(world, x, y, z, ore)) {
						if(world.getBlockMetadata(x, y, z) == oreStack.getItemDamage()) {
							world.setBlock(x, y, z, MetallurgyBees.beehive, 1, 0);
							((TileEntityMetadata) world.getTileEntity(x, y, z)).setTileMetadata(beeType.ordinal());
							return true;
						}
					}
				}
			}
		}
		return false;
	}

}
