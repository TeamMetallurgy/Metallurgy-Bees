package elcon.mods.metallurgybees;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.oredict.OreDictionary;

import com.teammetallurgy.metallurgy.api.MetalType;
import com.teammetallurgy.metallurgy.api.MetallurgyApi;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.metallurgybees.Metals.Metal;
import elcon.mods.metallurgybees.blocks.BlockBeehive;
import elcon.mods.metallurgybees.blocks.BlockExtendedMetadata;
import elcon.mods.metallurgybees.items.HiveDrop;
import elcon.mods.metallurgybees.items.ItemBeeGun;
import elcon.mods.metallurgybees.items.ItemBlockExtendedMetadata;
import elcon.mods.metallurgybees.items.ItemHiveFrame;
import elcon.mods.metallurgybees.items.ItemHoneyComb;
import elcon.mods.metallurgybees.tileentities.TileEntityExtended;
import elcon.mods.metallurgybees.tileentities.TileEntityMetadata;
import elcon.mods.metallurgybees.types.MetallurgyBeeTypes;
import elcon.mods.metallurgybees.types.MetallurgyFrameTypes;
import elcon.mods.metallurgybees.worldgen.WorldGenBeehives;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IClassification;
import forestry.api.recipes.RecipeManagers;

@Mod(modid = MBReference.MOD_ID, name = MBReference.NAME, version = MBReference.VERSION, acceptedMinecraftVersions = MBReference.MC_VERSION, dependencies = MBReference.DEPENDENCIES)
public class MetallurgyBees {

	@Instance(MBReference.MOD_ID)
	public static MetallurgyBees instance;

	@SidedProxy(clientSide = MBReference.CLIENT_PROXY_CLASS, serverSide = MBReference.SERVER_PROXY_CLASS)
	public static MBCommonProxy proxy;

	public static MBConfig config;

	public static MBCreativeTabForestry creativeTab;

	public static HashMap<String, Block> beehives = new HashMap<String, Block>();
	public static Item honeyComb;
	public static Item hiveFrame;
	public static Item beeGun;

	public static IBeeRoot beeRoot;
	public static IClassification branchMetal;
	public static AlleleFlowers alleleFlowerStone;

	public static Material materialBeeHive;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		// init config
		config = new MBConfig(event.getSuggestedConfigurationFile());
		config.load();
		config.save();

		// init creative tab
		creativeTab = new MBCreativeTabForestry("MetallurgyAddonForestry");

		// init materials
		materialBeeHive = new MaterialBeeHive();

		// init and register beehive blocks
		
		String[] setNames = MetallurgyApi.getSetNames();
		for (String setName : setNames) {
			
			String blockSetName = setName.substring(0, 1).toUpperCase() + setName.substring(1).toLowerCase();
			Block beehive = new BlockBeehive().setSetName(setName).setBlockName("metallurgyBeehive" + blockSetName);
		
			GameRegistry.registerBlock(beehive, ItemBlockExtendedMetadata.class, "metallurgyBeehive" + blockSetName);
			
			beehives.put(setName, beehive);
		}
		
		MetallurgyFrameTypes.init();
		// init items
		honeyComb = new ItemHoneyComb().setUnlocalizedName("metallurgyHoneyComb");
		for(int i = 0; i < MetallurgyFrameTypes.values().length; i++) {
			MetallurgyFrameTypes frames = MetallurgyFrameTypes.values()[i];
			hiveFrame = new ItemHiveFrame(frames).setUnlocalizedName("metallurgyFrame");
		}
		beeGun = new ItemBeeGun().setUnlocalizedName("metallurgyBeegun");

		// register items
		GameRegistry.registerItem(honeyComb, "metallurgyHoneyComb");
		GameRegistry.registerItem(hiveFrame, "metallurgyFrame");

		// register tileentities
		GameRegistry.registerTileEntity(TileEntityExtended.class, "MetallurgyTileExtended");
		GameRegistry.registerTileEntity(TileEntityMetadata.class, "MetallurgyTileMetadata");
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// register render information
		proxy.registerRenderingInformation();

		// register events
		MinecraftForge.EVENT_BUS.register(this);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		// init metals
		Metals.init();

		// init frame types
		MetallurgyFrameTypes.init();


		// get bee root
		beeRoot = (IBeeRoot) AlleleManager.alleleRegistry.getSpeciesRoot("rootBees");

		// init flower provider
		alleleFlowerStone = new AlleleFlowers();
		AlleleManager.alleleRegistry.registerAllele(alleleFlowerStone);

		// init bee branches
		branchMetal = new BranchBees();
		AlleleManager.alleleRegistry.getClassification("family.apidae").addMemberGroup(branchMetal);


		for(int i = 0; i < MetallurgyBeeTypes.values().length; i++) {
			MetallurgyBeeTypes beeType = MetallurgyBeeTypes.values()[i];
			beeType.metal = Metals.getMetal(beeType.name);

			if (beeType.metal == null || beeType.metal.metalInfo == null)
				continue;

			beeType.hasHive = beeType.metal.metalInfo.getType() != MetalType.Alloy;

			beeType.speciesRough = new AlleleBeeSpecies("metallurgy.species." + beeType.name + "Rough", true, "metallurgy.bees." + beeType.name + ".rough", branchMetal, "metallum", beeType.colorBeeRoughPrimary, beeType.colorBeeRoughSecondary).addProduct(new ItemStack(honeyComb, 1, i), 30);
			if(beeType.metal.setName != "utility") {
				beeType.speciesRefined = new AlleleBeeSpecies("metallurgy.species." + beeType.name + "Refined", true, "metallurgy.bees." + beeType.name + ".refined", branchMetal, "metallum", beeType.colorBeeRefinedPrimary, beeType.colorBeeRefinedSecondary).addProduct(new ItemStack(honeyComb, 1, i), 50);
			}
			beeType.speciesReforged = new AlleleBeeSpecies("metallurgy.species." + beeType.name + "Reforged", true, "metallurgy.bees." + beeType.name + ".reforged", branchMetal, "metallum", beeType.colorBeeReforgedPrimary, beeType.colorBeeReforgedSecondary).addProduct(new ItemStack(honeyComb, 1, i), 70);

			// init bee species alleles
			if(beeType.metal.setName == "nether") {
				((AlleleBeeSpecies) beeType.speciesRough).setTemperature(EnumTemperature.HELLISH);
				((AlleleBeeSpecies) beeType.speciesRefined).setTemperature(EnumTemperature.HELLISH);
				((AlleleBeeSpecies) beeType.speciesReforged).setTemperature(EnumTemperature.HELLISH);
			}

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
					new BeeMutation(beeType.speciesRough, AlleleManager.alleleRegistry.getAllele(getBeeParent2(beeType)), getMetalBeeReforgedTemplate(beeType), 2);
				} else {
					new BeeMutation(beeType.speciesRough, AlleleManager.alleleRegistry.getAllele(getBeeParent1(beeType)), getMetalBeeRefinedTemplate(beeType), 5);
					new BeeMutation(beeType.speciesRefined, AlleleManager.alleleRegistry.getAllele(getBeeParent2(beeType)), getMetalBeeReforgedTemplate(beeType), 2);
				}
			}

			// register centrifuge recipes
			RecipeManagers.centrifugeManager.addRecipe(20, new ItemStack(honeyComb, 1, i), new ItemStack[]{getMetalDust(beeType.name), new ItemStack(GameRegistry.findItem("Forestry", "beeswax")), new ItemStack(GameRegistry.findItem("Forestry", "honeyDrop"))}, new int[]{25, 50, 25});

			// add hives and their drops
			if(beeType.hasHive) {
				beeType.hiveDrops.add(new HiveDrop(getMetalBeeRoughTemplate(beeType), new ItemStack[]{new ItemStack(honeyComb, 1, i)}, 80));

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
		}
		// create alloy bee mutations
		createAlloyBeeMutations();
	}

	public void createAlloyBeeMutations() {
		// TODO: Enable disabled mutations
		/*
		createMutations(MetallurgyBeeTypes.COPPER, MetallurgyBeeTypes.TIN, MetallurgyBeeTypes.BRONZE);
		createMutations(MetallurgyBeeTypes.BRONZE, MetallurgyBeeTypes.GOLD, MetallurgyBeeTypes.HEPATIZON);
		createMutations(MetallurgyBeeTypes.BRONZE, MetallurgyBeeTypes.IRON, MetallurgyBeeTypes.DAMASCUS_STEEL);
		createMutations(MetallurgyBeeTypes.IRON, MetallurgyBeeTypes.GOLD, MetallurgyBeeTypes.ANGMALLEN);
		createMutations(MetallurgyBeeTypes.IRON, MetallurgyBeeTypes.MANGANESE, MetallurgyBeeTypes.STEEL);
		createMutations(MetallurgyBeeTypes.ZINC, MetallurgyBeeTypes.COPPER, MetallurgyBeeTypes.BRASS);
		createMutations(MetallurgyBeeTypes.GOLD, MetallurgyBeeTypes.SILVER, MetallurgyBeeTypes.ELECTRUM);
		createMutations(MetallurgyBeeTypes.CERUCLASE, MetallurgyBeeTypes.ALDUORITE, MetallurgyBeeTypes.INOLASHITE);
		createMutations(MetallurgyBeeTypes.KALENDRITE, MetallurgyBeeTypes.PLATINUM, MetallurgyBeeTypes.AMORDRINE);
		createMutations(MetallurgyBeeTypes.DEEP_IRON, MetallurgyBeeTypes.INFUSCOLIUM, MetallurgyBeeTypes.BLACK_STEEL);
		createMutations(MetallurgyBeeTypes.MITHRIL, MetallurgyBeeTypes.SILVER, MetallurgyBeeTypes.QUICKSILVER);
		createMutations(MetallurgyBeeTypes.MITHRIL, MetallurgyBeeTypes.RUBRACIUM, MetallurgyBeeTypes.HADEROTH);
		createMutations(MetallurgyBeeTypes.ORICHALCUM, MetallurgyBeeTypes.PLATINUM, MetallurgyBeeTypes.CELENEGIL);
		createMutations(MetallurgyBeeTypes.ADAMANTINE, MetallurgyBeeTypes.ATLARUS, MetallurgyBeeTypes.TARTARITE);
		createMutations(MetallurgyBeeTypes.EXIMITE, MetallurgyBeeTypes.MEUTOITE, MetallurgyBeeTypes.DESICHALKOS);
		*/
	}

	public void createMutations(MetallurgyBeeTypes parent1, MetallurgyBeeTypes parent2, MetallurgyBeeTypes child) {
		new BeeMutation(parent1.speciesRough, parent2.speciesRough, getMetalBeeRoughTemplate(child), 10);
		new BeeMutation(child.speciesRough, AlleleManager.alleleRegistry.getAllele(getBeeParent1(child)), getMetalBeeRefinedTemplate(child), 5);
		new BeeMutation(child.speciesRefined, AlleleManager.alleleRegistry.getAllele(getBeeParent2(child)), getMetalBeeReforgedTemplate(child), 2);
	}

	private String getBeeParent1(MetallurgyBeeTypes types) {
		if(types.metal.setName == "base") {
			return "forestry.speciesUnweary";

		} else if(types.metal.setName == "precious") {
			return "forestry.speciesMajestic";

		} else if(types.metal.setName == "nether") {
			return "forestry.speciesFiendish";

		} else if(types.metal.setName == "fantasy") {
			return "forestry.speciesValiant";

		} else if(types.metal.setName == "ender") {
			return "forestry.speciesSpectral";

		} else if(types.metal.setName == "utility") {
			return "forestry.speciesRural";
		}
		if(types.name == "iron") {
			return "forestry.speciesUnweary";

		} else if(types.name == "gold") {
			return "forestry.speciesMajestic";
		}
		return "";
	}

	private String getBeeParent2(MetallurgyBeeTypes types) {
		if(types.metal.setName == "base") {
			return "forestry.speciesIndustrious";

		} else if(types.metal.setName == "precious") {
			return "forestry.speciesImperial";

		} else if(types.metal.setName == "nether") {
			return "forestry.speciesDemonic";

		} else if(types.metal.setName == "fantasy") {
			return "forestry.speciesHeroic";

		} else if(types.metal.setName == "ender") {
			return "forestry.speciesPhantasmal";

		} else if(types.metal.setName == "utility") {
			return "forestry.speciesRural";
		}
		if(types.name == "iron") {
			return "forestry.speciesIndustrious";

		} else if(types.name == "gold") {
			return "forestry.speciesImperial";
		}
		return "";
	}

	public ItemStack getMetalDust(String beeType) {
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

	public IAllele[] getDefaultMetalBeeTemplate() {
		IAllele[] alleles = beeRoot.getDefaultTemplate();
		alleles[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = alleleFlowerStone;
		alleles[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.boolTrue");
		return alleles;
	}

	public IAllele[] getMetalBeeRoughTemplate(MetallurgyBeeTypes beeType) {
		IAllele[] alleles = getDefaultMetalBeeTemplate();
		alleles[EnumBeeChromosome.SPECIES.ordinal()] = beeType.speciesRough;
		return alleles;
	}

	public IAllele[] getMetalBeeRefinedTemplate(MetallurgyBeeTypes beeType) {
		IAllele[] alleles = getDefaultMetalBeeTemplate();
		alleles[EnumBeeChromosome.SPECIES.ordinal()] = beeType.speciesRefined;
		return alleles;
	}

	public IAllele[] getMetalBeeReforgedTemplate(MetallurgyBeeTypes beeType) {
		IAllele[] alleles = getDefaultMetalBeeTemplate();
		alleles[EnumBeeChromosome.SPECIES.ordinal()] = beeType.speciesReforged;
		return alleles;
	}

	@SubscribeEvent
	public void onBlockBreak(BreakEvent event) {
		if(event.block != null) {
			if(beehives.containsValue(event.block)) {
				event.setCanceled(true);
				ItemStack stack = event.getPlayer().getCurrentEquippedItem();
				if(stack != null && stack.getItem().onBlockStartBreak(stack, event.x, event.y, event.z, event.getPlayer())) {
					return;
				}
				Block block = event.world.getBlock(event.x, event.y, event.z);
				int meta = ((BlockExtendedMetadata) event.block).getMetadata(event.world, event.x, event.y, event.z);
				event.world.playAuxSFXAtEntity(event.getPlayer(), 2001, event.x, event.y, event.z, Block.getIdFromBlock(block) + (meta << 12));
				boolean flag = false;
				if(event.getPlayer().capabilities.isCreativeMode) {
					flag = removeBlock(event.world, event.x, event.y, event.z, meta, event.getPlayer(), true);
					((EntityPlayerMP) event.getPlayer()).playerNetServerHandler.sendPacket(new S23PacketBlockChange(event.x, event.y, event.z, event.world));
				} else {
					ItemStack itemstack = event.getPlayer().getCurrentEquippedItem();
					boolean flag1 = false;
					if(block != null) {
						flag1 = block.canHarvestBlock(event.getPlayer(), meta);
					}
					if(itemstack != null) {
						block.onBlockDestroyedByPlayer(event.world, event.x, event.y, event.z, meta);
						if(itemstack.stackSize == 0) {
							event.getPlayer().destroyCurrentEquippedItem();
						}
					}
					flag = removeBlock(event.world, event.x, event.y, event.z, meta, event.getPlayer(), flag1);
					if(flag && flag1) {
						block.harvestBlock(event.world, event.getPlayer(), event.x, event.y, event.z, meta);
					}
				}
				if(!event.getPlayer().capabilities.isCreativeMode && flag && event != null) {
					block.dropXpOnBlockBreak(event.world, event.x, event.y, event.z, event.getExpToDrop());
				}
			}
		}
	}

	private boolean removeBlock(World world, int x, int y, int z, int meta, EntityPlayer player, boolean drop) {
		Block block = world.getBlock(x, y, z);
		if(block != null) {
			block.onBlockHarvested(world, x, y, z, meta, player);
		}
		if(drop) {
			boolean flag = (block != null && world.setBlockToAir(x, y, z));
			if(block != null && flag) {
				block.onBlockDestroyedByPlayer(world, x, y, z, meta);
			}
			return flag;
		} else {
			boolean flag = (block != null && world.setBlockToAir(x, y, z));
			if(block != null && flag) {
				block.onBlockDestroyedByPlayer(world, x, y, z, meta);
			}
			return flag;
		}
	}
}
