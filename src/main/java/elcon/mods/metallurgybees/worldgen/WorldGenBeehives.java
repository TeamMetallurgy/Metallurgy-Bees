package elcon.mods.metallurgybees.worldgen;

import cpw.mods.fml.common.IWorldGenerator;
import elcon.mods.metallurgybees.MetallurgyBees;
import elcon.mods.metallurgybees.Metals;
import elcon.mods.metallurgybees.types.MetallurgyBeeTypes;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenBeehives extends WorldGenerator implements IWorldGenerator {

    public MetallurgyBeeTypes beeType;

    public WorldGenBeehives(MetallurgyBeeTypes beeType) {
        this.beeType = beeType;
    }

    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        for (int i = 0; i < 30; i++) {
            int x = chunkX * 16 + rand.nextInt(16);
            int y = 5 + rand.nextInt(64);
            int z = chunkZ * 16 + rand.nextInt(16);
            generate(world, rand, x, y, z);
        }
    }

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        if (block != null) {
            ItemStack ore = Metals.getMetal(beeType.name).ore.getOre(beeType.name);
            if (ore != null) {
                if (block.isReplaceableOreGen(world, x, y, z, Block.getBlockFromItem(ore.getItem()))) {
                    if (world.getBlockMetadata(x, y, z) == ore.getItemDamage()) {
                        world.setBlock(x, y, z, MetallurgyBeeTypes.valueOf(beeType.name.toUpperCase().replace(" ", "_")).beeHive, 1, 0);
                        //((TileEntityMetadata) world.getTileEntity(x, y, z)).setTileMetadata(beeType.ordinal());
                        return true;
                    }
                }
            }
        }
        return false;


    }
}
