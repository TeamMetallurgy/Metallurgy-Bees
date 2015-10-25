package elcon.mods.metallurgybees;

import java.util.EnumSet;
import java.util.Set;

import forestry.api.apiculture.FlowerManager;
import forestry.api.genetics.IFlower;
import forestry.api.genetics.IFlowerProvider;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IPollinatable;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.oredict.OreDictionary;

public class FlowerProviderStone implements IFlowerProvider {

	public FlowerProviderStone() {
		registerAcceptableFlower(Blocks.stone, OreDictionary.WILDCARD_VALUE);
	}
	
	@Override
	public Set<IFlower> getFlowers() {
		return FlowerManager.flowerRegistry.getAcceptableFlowers(getFlowerType());
	}
	
	protected void registerPlantableFlower(Block block, int meta, int weight) {
		FlowerManager.flowerRegistry.registerPlantableFlower(block, meta, weight, getFlowerType());
	}
	
	protected void registerAcceptableFlower(Block block, int meta) {
		FlowerManager.flowerRegistry.registerAcceptableFlower(block, meta, getFlowerType());
	}
	
	@Override
	@Deprecated
	public boolean growFlower(World world, IIndividual genome, int x, int i, int j) {
		return true;
	}
	
	@Override
	public boolean isAcceptedPollinatable(World world, IPollinatable pollinatable) {
		EnumSet<EnumPlantType> types = pollinatable.getPlantType();
		return (types.size() > 1) || (!types.contains(EnumPlantType.Nether));
	}

	@Override
	public String getDescription() {
		return StatCollector.translateToLocal(getFlowerType());
	}

	@Override
	public ItemStack[] affectProducts(World world, IIndividual individual, int x, int y, int z, ItemStack[] products) {
		return products;
	}

	@Override
	public String getFlowerType() {
		return "flowers.stone";
	}
}
