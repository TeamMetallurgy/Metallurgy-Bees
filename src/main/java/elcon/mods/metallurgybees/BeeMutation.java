package elcon.mods.metallurgybees;

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.world.World;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeMutation;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;

public class BeeMutation implements IBeeMutation {

	public IAllele parent1 = null;
	public IAllele parent2 = null;
	public IAllele[] template = new IAllele[0];
	public int chance;

	public BeeMutation(IAllele parent1, IAllele parent2, IAllele[] template, int chance) {
		this.parent1 = parent1;
		this.parent2 = parent2;
		this.template = template;
		this.chance = chance;
		this.getRoot().registerMutation(this);
	}

	@Override
	public IAllele getAllele0() {
		return parent1;
	}

	@Override
	public IAllele getAllele1() {
		return parent2;
	}

	@Override
	public IAllele[] getTemplate() {
		return template;
	}

	@Override
	public float getBaseChance() {
		return chance;
	}

	@Override
	public Collection<String> getSpecialConditions() {
		return new ArrayList<String>();
	}

	@Override
	public boolean isPartner(IAllele allele) {
		return parent1.getUID().equals(allele.getUID()) || parent2.getUID().equals(allele.getUID());
	}

	@Override
	public IAllele getPartner(IAllele allele) {
		IAllele val = parent1;
		if(val.getUID().equals(allele.getUID()))
			val = parent2;
		return val;
	}

	@Override
	public boolean isSecret() {
		return false;
	}

	@Override
	public IBeeRoot getRoot() {
		return MetallurgyBees.beeRoot;
	}

	@Override
	public float getChance(IBeeHousing housing, IAllele allele0, IAllele allele1, IGenome genome0, IGenome genome1) {
		World world = housing.getWorld();

		int processedChance = Math.round(this.chance * housing.getMutationModifier((IBeeGenome) genome0, (IBeeGenome) genome1, 1.0F) * getRoot().getBeekeepingMode(world).getMutationModifier((IBeeGenome) genome0, (IBeeGenome) genome1, 1.0F));

		if(processedChance <= 0.0F) {
			return 0.0F;
		}
		if((this.parent1.getUID().equals(allele0.getUID())) && (this.parent2.getUID().equals(allele1.getUID())))
			return processedChance;
		if((this.parent2.getUID().equals(allele0.getUID())) && (this.parent1.getUID().equals(allele1.getUID()))) {
			return processedChance;
		}
		return 0.0F;
	}
}