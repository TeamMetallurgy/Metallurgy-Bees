package elcon.mods.metallurgybees.bees;

import java.util.Hashtable;
import java.util.Map;

import com.teammetallurgy.metallurgy.api.MetalType;

import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.metallurgybees.MBConfig;
import elcon.mods.metallurgybees.Metals;
import elcon.mods.metallurgybees.Metals.Metal;
import elcon.mods.metallurgybees.items.HiveDrop;
import elcon.mods.metallurgybees.items.MBItems;
import elcon.mods.metallurgybees.types.MetallurgyBeeTypes;
import elcon.mods.metallurgybees.util.LogHelper;
import elcon.mods.metallurgybees.util.compat.MagicBeesHelper;
import elcon.mods.metallurgybees.worldgen.WorldGenBeehives;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IClassification;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.oredict.OreDictionary;

public class BeeManager {

	public static IBeeRoot beeRoot;
	public static IClassification branchMetal;
	public static AlleleFlowers alleleFlowerStone;

	public static void getBeeRoot() {
		beeRoot = (IBeeRoot) AlleleManager.alleleRegistry.getSpeciesRoot("rootBees");
	}

	public static void setupAlleles() {
		// init flower provider
		setupFlower();
		// init bee branches
		setupBranch();
		// init bee
		setupBee();
		// create mutation
		setupMutation();
	}
	
	private static void setupFlower(){
		alleleFlowerStone = new AlleleFlowers();
		AlleleManager.alleleRegistry.registerAllele(alleleFlowerStone);
	}

	private static void setupBranch(){
		branchMetal = new BranchBees();
		AlleleManager.alleleRegistry.getClassification("family.apidae").addMemberGroup(branchMetal);
	}
	
	private static void setupBee(){
		int i = 0;
		for(MetallurgyBeeTypes beeType : MetallurgyBeeTypes.values()) {
			beeType.metal = Metals.getMetal(beeType.name);

			if (beeType.metal == null || beeType.metal.metalInfo == null){
				LogHelper.error("No metal found for "+beeType.name);
				i++;
				continue;
			}

			beeType.hasHive = beeType.metal.metalInfo.getType() != MetalType.Alloy;

			beeType.speciesRough = new AlleleBeeSpecies("metallurgy.species." + beeType.name + "Rough", true, "metallurgy.bees." + beeType.name + ".rough", branchMetal, "metallum", beeType.colorBeeRoughPrimary, beeType.colorBeeRoughSecondary).addProduct(new ItemStack(MBItems.honeyComb, 1, i), 0.3f);
			if(beeType.metal.setName != "utility") {
				beeType.speciesRefined = new AlleleBeeSpecies("metallurgy.species." + beeType.name + "Refined", true, "metallurgy.bees." + beeType.name + ".refined", branchMetal, "metallum", beeType.colorBeeRefinedPrimary, beeType.colorBeeRefinedSecondary).addProduct(new ItemStack(MBItems.honeyComb, 1, i), 0.5f);
			}
			beeType.speciesReforged = new AlleleBeeSpecies("metallurgy.species." + beeType.name + "Reforged", true, "metallurgy.bees." + beeType.name + ".reforged", branchMetal, "metallum", beeType.colorBeeReforgedPrimary, beeType.colorBeeReforgedSecondary).addProduct(new ItemStack(MBItems.honeyComb, 1, i), 0.7f);


			// register templates
			AlleleManager.alleleRegistry.registerAllele(beeType.speciesRough);
			beeRoot.registerTemplate(getMetalBeeRoughTemplate(beeType));

			if(beeType.metal.setName != "utility") {
				AlleleManager.alleleRegistry.registerAllele(beeType.speciesRefined);
				beeRoot.registerTemplate(getMetalBeeRefinedTemplate(beeType));
			}
			AlleleManager.alleleRegistry.registerAllele(beeType.speciesReforged);
			beeRoot.registerTemplate(getMetalBeeReforgedTemplate(beeType));

			// init bee mutations
			if(beeType.hasHive) {
				if(beeType.metal.setName == "utility") {
					new BeeMutation(beeType.speciesRough, getBeeParent2(beeType), getMetalBeeReforgedTemplate(beeType), 2);
				} else {
					BeeMutation mutation1 = new BeeMutation(beeType.speciesRough, getBeeParent1(beeType), getMetalBeeRefinedTemplate(beeType), 5);
					BeeMutation mutation2 = new BeeMutation(beeType.speciesRefined, getBeeParent2(beeType), getMetalBeeReforgedTemplate(beeType), 2);
					switch(beeType.metal.setName){
						case "nether" :	
							mutation1.setBiomeRequired(BiomeDictionary.Type.NETHER);
							mutation2.setBiomeRequired(BiomeDictionary.Type.NETHER);
							break;
						case "ender" :
							mutation1.setBiomeRequired(BiomeDictionary.Type.END);
							mutation2.setBiomeRequired(BiomeDictionary.Type.END);
							break;
					}
				}
			}

			// register centrifuge recipes
			Map<ItemStack, Float> output = new Hashtable<ItemStack, Float>();
			output.put(getMetalDust(beeType.name), 0.25f);
			output.put(new ItemStack(GameRegistry.findItem("Forestry", "beeswax")), 0.5f);
			output.put(new ItemStack(GameRegistry.findItem("Forestry", "honeyDrop")), 0.25f);
			RecipeManagers.centrifugeManager.addRecipe(20, new ItemStack(MBItems.honeyComb, 1, i), output);

			// add hives and their drops
			if(beeType.hasHive) {
				beeType.hiveDrops.add(new HiveDrop(getMetalBeeRoughTemplate(beeType), new ItemStack[]{new ItemStack(MBItems.honeyComb, 1, i)}, 80));

				// add worldgen
				GameRegistry.registerWorldGenerator(new WorldGenBeehives(beeType), 5);
			}

			Metal metal = Metals.getMetal(beeType.name);
			if(metal != null) {
				ItemStack ore = metal.metalSet.getOre(metal.metalInfo.getName());
				if(ore != null) {
					// TODO: fix mining level
					//MinecraftForge.setBlockHarvestLevel(beehive, i, "pickaxe", MinecraftForge.getBlockHarvestLevel(Block.blocksList[ore.itemID], ore.getItemDamage(), "pickaxe"));
				}
			}
			i++;
		}
	}
	
	private static void setupMutation() {
		if(MBConfig.enableSpecialBeesMutation){
			createMutations(MetallurgyBeeTypes.COPPER, MetallurgyBeeTypes.TIN, MetallurgyBeeTypes.BRONZE);
			//createMutations(MetallurgyBeeTypes.BRONZE, MetallurgyBeeTypes.GOLD, MetallurgyBeeTypes.HEPATIZON);
			//createMutations(MetallurgyBeeTypes.BRONZE, MetallurgyBeeTypes.IRON, MetallurgyBeeTypes.DAMASCUS_STEEL);
			//createMutations(MetallurgyBeeTypes.IRON, MetallurgyBeeTypes.GOLD, MetallurgyBeeTypes.ANGMALLEN);
			//createMutations(MetallurgyBeeTypes.IRON, MetallurgyBeeTypes.MANGANESE, MetallurgyBeeTypes.STEEL);
			createMutations(MetallurgyBeeTypes.ZINC, MetallurgyBeeTypes.COPPER, MetallurgyBeeTypes.BRASS);
			//createMutations(MetallurgyBeeTypes.GOLD, MetallurgyBeeTypes.SILVER, MetallurgyBeeTypes.ELECTRUM);
			createMutations(MetallurgyBeeTypes.CERUCLASE, MetallurgyBeeTypes.ALDUORITE, MetallurgyBeeTypes.INOLASHITE);
			createMutations(MetallurgyBeeTypes.KALENDRITE, MetallurgyBeeTypes.PLATINUM, MetallurgyBeeTypes.AMORDRINE);
			createMutations(MetallurgyBeeTypes.DEEP_IRON, MetallurgyBeeTypes.INFUSCOLIUM, MetallurgyBeeTypes.BLACK_STEEL);
			createMutations(MetallurgyBeeTypes.MITHRIL, MetallurgyBeeTypes.SILVER, MetallurgyBeeTypes.QUICKSILVER);
			createMutations(MetallurgyBeeTypes.MITHRIL, MetallurgyBeeTypes.RUBRACIUM, MetallurgyBeeTypes.HADEROTH);
			createMutations(MetallurgyBeeTypes.ORICHALCUM, MetallurgyBeeTypes.PLATINUM, MetallurgyBeeTypes.CELENEGIL);
			createMutations(MetallurgyBeeTypes.ADAMANTINE, MetallurgyBeeTypes.ATLARUS, MetallurgyBeeTypes.TARTARITE);
			createMutations(MetallurgyBeeTypes.EXIMITE, MetallurgyBeeTypes.MEUTOITE, MetallurgyBeeTypes.DESICHALKOS);
		}
	}
	
	private static void createMutations(MetallurgyBeeTypes parent1, MetallurgyBeeTypes parent2, MetallurgyBeeTypes child) {
		BeeMutation mutation1 = new BeeMutation(parent1.speciesRough, parent2.speciesRough, getMetalBeeRoughTemplate(child), 10);
		BeeMutation mutation2 = new BeeMutation(child.speciesRough, getBeeParent1(child), getMetalBeeRefinedTemplate(child), 5);
		BeeMutation mutation3 = new BeeMutation(child.speciesRefined, getBeeParent2(child), getMetalBeeReforgedTemplate(child), 2);
		switch(child.metal.setName){
			case "nether" :	
				mutation1.setBiomeRequired(BiomeDictionary.Type.NETHER);
				mutation2.setBiomeRequired(BiomeDictionary.Type.NETHER);
				mutation3.setBiomeRequired(BiomeDictionary.Type.NETHER);
				break;
			case "ender" :
				mutation1.setBiomeRequired(BiomeDictionary.Type.END);
				mutation2.setBiomeRequired(BiomeDictionary.Type.END);
				mutation3.setBiomeRequired(BiomeDictionary.Type.END);
				break;
		}
	}
	
	private static IAlleleSpecies getBeeParent1(MetallurgyBeeTypes types) {
		switch(types.metal.setName){
			case "base" :
				return getBaseSpecies("Unweary");
			case "precious" :
				return getBaseSpecies("Majestic");
			case "nether" :
				return getBaseSpecies("Fiendish");
			case "fantasy" :
				return getBaseSpecies("Valiant");
			case "ender" :
				return getBaseSpecies("Spectral");
			case "utility" :
				return getBaseSpecies("Rural");
		}

		if(types.name == "iron") {
			return getBaseSpecies("Unweary");
		} else if(types.name == "gold") {
			return getBaseSpecies("Majestic");
		}
		return null;
	}

	private static IAlleleSpecies getBeeParent2(MetallurgyBeeTypes types) {
		switch(types.metal.setName){
			case "base" :
				return getBaseSpecies("Industrious");
			case "precious" :
				return getBaseSpecies("Imperial");
			case "nether" :
				return getBaseSpecies("Demonic");
			case "fantasy" :
				return getBaseSpecies("Heroic");
			case "ender" :
				return getBaseSpecies("Phantasmal");
			case "utility" :
				return getBaseSpecies("Rural");
		}

		if(types.name == "iron") {
			return getBaseSpecies("Industrious");
		} else if(types.name == "gold") {
			return getBaseSpecies("Imperial");
		}
		return null;
	}

	private static ItemStack getMetalDust(String beeType) {
		if(beeType.equalsIgnoreCase("iron")) {
			return OreDictionary.getOres("dustIron").get(0);
		} else if(beeType.equalsIgnoreCase("gold")) {
			return OreDictionary.getOres("dustGold").get(0);
		} else if(Metals.getMetal(beeType).setName.equalsIgnoreCase("Utility")) {
			Metal metal = Metals.getMetal(beeType);
			ItemStack drop = metal.metalSet.getDrop(metal.metalInfo.getName()).copy();
			drop.stackSize = 1;
			return drop;
		}
		Metal metal = Metals.getMetal(beeType);
		return metal.metalSet.getDust(metal.metalInfo.getName()).copy();
	}
	
	private static IAlleleBeeSpecies getBaseSpecies(String name) {
		return (IAlleleBeeSpecies) AlleleManager.alleleRegistry.getAllele((new StringBuilder()).append("forestry.species").append(name).toString());
	}

	private static IAllele[] getMetalBeeRoughTemplate(MetallurgyBeeTypes beeType) {
		IAllele[] allelespecies = setBeeChromosome(beeRoot.getDefaultTemplate(), beeType, beeType.speciesRough);
		return allelespecies;
	}

	private static IAllele[] getMetalBeeRefinedTemplate(MetallurgyBeeTypes beeType) {
		IAllele[] allelespecies = setBeeChromosome(beeRoot.getDefaultTemplate(), beeType, beeType.speciesRefined);
		return allelespecies;
	}

	private static IAllele[] getMetalBeeReforgedTemplate(MetallurgyBeeTypes beeType) {
		IAllele[] allelespecies = setBeeChromosome(beeRoot.getDefaultTemplate(), beeType, beeType.speciesReforged);
		return allelespecies;
	}

	private static IAllele[] setBeeChromosome(IAllele[] allelespecies, MetallurgyBeeTypes beeType, IAlleleSpecies beeSpecies){
		allelespecies[EnumBeeChromosome.SPECIES.ordinal()] = beeSpecies;
		allelespecies[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.boolTrue");
		switch(beeType.metal.setName){
			case "nether" :
				switch(beeType.name){
					case "vyroxeres" :
						allelespecies[EnumBeeChromosome.EFFECT.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.effectMiasmic");
						break;
					case "kalendrite" :
					case "amordrine" :
						allelespecies[EnumBeeChromosome.EFFECT.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.effectBeatific");
						break;
					default:
						allelespecies[EnumBeeChromosome.EFFECT.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.effectAggressive");
						break;
				}
				((AlleleBeeSpecies) beeSpecies).setHasEffect();
				((AlleleBeeSpecies) beeSpecies).setNocturnal();
				((AlleleBeeSpecies) beeSpecies).setTemperature(EnumTemperature.HELLISH);
				((AlleleBeeSpecies) beeSpecies).setHumidity(EnumHumidity.ARID);
				allelespecies[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.flowersNether");
				allelespecies[EnumBeeChromosome.NOCTURNAL.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.boolTrue");
				break;
			case "ender" :
				allelespecies[EnumBeeChromosome.EFFECT.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.effectMisanthrope");
				allelespecies[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.flowersEnd");
				((AlleleBeeSpecies) beeSpecies).setHasEffect();
				((AlleleBeeSpecies) beeSpecies).setTemperature(EnumTemperature.COLD);
				break;
			default :
				allelespecies[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = alleleFlowerStone;
				break;
		}

		switch(beeType.name){
			case "quicksilver" :
				if(MagicBeesHelper.isActive() && MBConfig.magicBeesActive){
					((AlleleBeeSpecies) beeSpecies).setHasEffect();
					allelespecies[EnumBeeChromosome.EFFECT.ordinal()] = AlleleManager.alleleRegistry.getAllele("magicbees.effectMoveSpeed");
				}
				break;
		}

		return allelespecies;
	}
}
