package elcon.mods.metallurgybees.items;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import elcon.mods.metallurgybees.MetallurgyBees;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.apiculture.IHiveDrop;
import forestry.api.genetics.IAllele;

public class HiveDrop implements IHiveDrop {
	
	public IAllele[] beeTemplate;
	private ArrayList<ItemStack> additional = new ArrayList<ItemStack>();
	public int chance;
	private float ignoblePercent = 0f;

	public HiveDrop(IAllele[] template, ItemStack[] bonus, int chance) {
		this.beeTemplate = template;
		this.chance = chance;
		for(ItemStack stack : bonus) {
			this.additional.add(stack);
		}

		if(MetallurgyBees.enableIgnobleBees){
			ignoblePercent = 0.7f;
		}
	}

	@Override
	public ItemStack getPrincess(World world, int x, int y, int z, int fortune) {
		IBee bee = getBee(world);
		if (world.rand.nextFloat() < ignoblePercent) {
			bee.setIsNatural(false);
		}

		return getRoot().getMemberStack(bee, EnumBeeType.PRINCESS.ordinal());
	}

	@Override
	public ArrayList<ItemStack> getDrones(World world, int x, int y, int z, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(getRoot().getMemberStack(getBee(world), EnumBeeType.DRONE.ordinal()));
		return ret;
	}

	@Override
	public ArrayList<ItemStack> getAdditional(World world, int x, int y, int z, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
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
	
	private IBee getBee(World w) {
		IBee bee = BeeManager.beeRoot.getBee(w, BeeManager.beeRoot.templateAsGenome(this.beeTemplate));
		if (w.rand.nextFloat() < this.ignoblePercent) {
			bee.setIsNatural(false);
		}
		
		return bee;
	}
}
