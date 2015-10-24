package elcon.mods.metallurgybees;

import java.util.ArrayList;
import java.util.Collection;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IBeeMutation;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import net.minecraft.world.World;

public class BeeMutation implements IBeeMutation {

	public IAlleleSpecies parent1 = null;
	public IAlleleSpecies parent2 = null;
	public IAllele[] template = new IAlleleSpecies[0];
	public int chance;

	public BeeMutation(IAlleleSpecies parent1, IAlleleSpecies parent2, IAllele[] template, int chance) {
		this.parent1 = parent1;
		this.parent2 = parent2;
		this.template = template;
		this.chance = chance;
		this.getRoot().registerMutation(this);
	}

	@Override
	public IAlleleSpecies getAllele0() {
		return parent1;
	}

	@Override
	public IAlleleSpecies getAllele1() {
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
	public float getChance(IBeeHousing housing, IAlleleBeeSpecies allele0, IAlleleBeeSpecies allele1, IBeeGenome genome0, IBeeGenome genome1) {
		World world = housing.getWorld();

		IBeeModifier housingBeeModifier = BeeManager.beeRoot.createBeeHousingModifier(housing);
		IBeeModifier modeBeeModifier = BeeManager.beeRoot.getBeekeepingMode(housing.getWorld()).getBeeModifier();
		
		int processedChance = Math.round(this.chance * housingBeeModifier.getMutationModifier((IBeeGenome) genome0, (IBeeGenome) genome1, 1.0F) * modeBeeModifier.getMutationModifier((IBeeGenome) genome0, (IBeeGenome) genome1, 1.0F));

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
