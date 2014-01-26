package elcon.mods.metallurgybees.items;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import elcon.mods.metallurgybees.MetallurgyBees;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.apiculture.IHiveDrop;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;

public class HiveDrop implements IHiveDrop {
	IAllele[] template;
	private ArrayList<ItemStack> additional = new ArrayList();
	int chance;

	public HiveDrop(IAllele[] template, ItemStack[] bonus, int chance) {
		this.template = template;
		this.chance = chance;
		for(ItemStack stack : bonus)
			this.additional.add(stack);
	}

	@Override
	public ItemStack getPrincess(World world, int x, int y, int z, int fortune) {
		return getRoot().getMemberStack(getRoot().getBee(world, getRoot().templateAsGenome(this.template)), EnumBeeType.PRINCESS.ordinal());
	}

	@Override
	public ArrayList<ItemStack> getDrones(World world, int x, int y, int z, int fortune) {
		ArrayList ret = new ArrayList();
		ret.add(getRoot().getMemberStack(getRoot().templateAsIndividual(this.template), EnumBeeType.DRONE.ordinal()));
		return ret;
	}

	@Override
	public ArrayList<ItemStack> getAdditional(World world, int x, int y, int z, int fortune) {
		ArrayList ret = new ArrayList();
		for(ItemStack stack : this.additional) {
			ret.add(stack.copy());
		}
		return ret;
	}

	@Override
	public int getChance(World world, int x, int y, int z) {
		return chance;
	}

	public IBeeRoot getRoot() {
		return MetallurgyBees.beeRoot;
	}

}
