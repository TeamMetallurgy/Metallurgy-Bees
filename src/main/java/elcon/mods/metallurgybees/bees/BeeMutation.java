package elcon.mods.metallurgybees.bees;

import java.util.ArrayList;
import java.util.Collection;

import elcon.mods.metallurgybees.util.LogHelper;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IBeeMutation;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class BeeMutation implements IBeeMutation {

	public IAlleleSpecies parent1;
	public IAlleleSpecies parent2;
	public IAllele[] template = new IAlleleSpecies[0];
	public int chance;
	private BiomeDictionary.Type requiredBiomeType;

	public BeeMutation(IAlleleSpecies parent1, IAlleleSpecies parent2, IAllele[] template, int chance) {
		this.parent1 = parent1;
		this.parent2 = parent2;
		this.template = template;
		this.chance = chance;
		this.requiredBiomeType = null;
		BeeManager.beeRoot.registerMutation(this);
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
		ArrayList<String> conditions = new ArrayList<String>();
		
		if (this.requiredBiomeType != null) {
			String biomeName = this.requiredBiomeType.name().substring(0, 1) + this.requiredBiomeType.name().substring(1).toLowerCase();
			conditions.add(String.format(StatCollector.translateToLocal("research.requiresBiome"), biomeName));
		}

		return conditions;
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
		return BeeManager.beeRoot;
	}

	@Override
	public float getChance(IBeeHousing housing, IAlleleBeeSpecies allele0, IAlleleBeeSpecies allele1, IBeeGenome genome0, IBeeGenome genome1) {
		World world = housing.getWorld();
		ChunkCoordinates housingCoords = housing.getCoordinates();

		IBeeModifier housingBeeModifier = BeeManager.beeRoot.createBeeHousingModifier(housing);
		IBeeModifier modeBeeModifier = BeeManager.beeRoot.getBeekeepingMode(housing.getWorld()).getBeeModifier();
		
		if (this.arePartners(allele0, allele1)) {
			if (this.requiredBiomeType != null) {
				BiomeDictionary.Type[] types = BiomeDictionary.getTypesForBiome(housing.getWorld().getBiomeGenForCoords(housingCoords.posX, housingCoords.posZ));
				boolean found = false;
				for (Type type : types) {
					if (this.requiredBiomeType == type) {
						found = true;
						break;
					}
				}
				if (!found) {
					return 0.0f;
				}
			}
			
			int processedChance = Math.round(this.chance * housingBeeModifier.getMutationModifier((IBeeGenome) genome0, (IBeeGenome) genome1, 1.0F) * modeBeeModifier.getMutationModifier((IBeeGenome) genome0, (IBeeGenome) genome1, 1.0F));

			if(processedChance <= 0.0F) {
				return 0.0F;
			}
			
			return processedChance;
		}

		return 0.0f;
	}
	
	public boolean arePartners(IAllele alleleA, IAllele alleleB) {
		return (this.parent1.getUID().equals(alleleA.getUID())) && this.parent2.getUID().equals(alleleB.getUID()) ||
				this.parent1.getUID().equals(alleleB.getUID()) && this.parent2.getUID().equals(alleleA.getUID());
	}
	
	public BeeMutation setBiomeRequired(BiomeDictionary.Type biomeType) {
		this.requiredBiomeType = biomeType;

		return this;
	}
}
