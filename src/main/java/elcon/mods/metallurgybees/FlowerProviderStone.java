package elcon.mods.metallurgybees;

import java.util.EnumSet;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import forestry.api.genetics.IFlowerProvider;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IPollinatable;

public class FlowerProviderStone implements IFlowerProvider {

	@Override
	public boolean isAcceptedFlower(World world, IIndividual individual, int x, int y, int z) {
		return world.getBlock(x, y, z) == Blocks.stone;
	}

	@Override
	public boolean isAcceptedPollinatable(World world, IPollinatable pollinatable) {
		EnumSet types = pollinatable.getPlantType();
		return (types.size() > 1) || (!types.contains(EnumPlantType.Nether));
	}

	@Override
	public boolean growFlower(World world, IIndividual individual, int x, int y, int z) {
		return true;
	}

	@Override
	public String getDescription() {
		return StatCollector.translateToLocal(getUnlocalizedDescription());
	}
	
	public String getUnlocalizedDescription() {
		return "flowers.stone";
	}

	@Override
	public ItemStack[] affectProducts(World world, IIndividual individual, int x, int y, int z, ItemStack[] products) {
		return products;
	}

	@Override
	public ItemStack[] getItemStacks() {
		return new ItemStack[]{new ItemStack(Blocks.stone)};
	}
}
