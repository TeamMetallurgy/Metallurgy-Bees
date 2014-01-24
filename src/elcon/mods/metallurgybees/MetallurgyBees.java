package elcon.mods.metallurgybees;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet53BlockChange;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import rebelkeithy.mods.metallurgy.api.OreType;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.metallurgybees.Metals.Metal;
import elcon.mods.metallurgybees.blocks.BlockBeehive;
import elcon.mods.metallurgybees.blocks.BlockExtendedMetadata;
import elcon.mods.metallurgybees.items.HiveDrop;
import elcon.mods.metallurgybees.items.ItemBlockExtendedMetadata;
import elcon.mods.metallurgybees.items.ItemHiveFrame;
import elcon.mods.metallurgybees.items.ItemHoneyComb;
import elcon.mods.metallurgybees.tileentities.TileEntityExtended;
import elcon.mods.metallurgybees.tileentities.TileEntityMetadata;
import elcon.mods.metallurgybees.worldgen.WorldGenBeehives;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.core.ItemInterface;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IClassification;
import forestry.api.recipes.RecipeManagers;

@Mod(modid = MBReference.MOD_ID, name = MBReference.NAME, version = MBReference.VERSION, acceptedMinecraftVersions = MBReference.MC_VERSION, dependencies = MBReference.DEPENDENCIES)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, clientPacketHandlerSpec = @SidedPacketHandler(channels = {"MetallurgyAddons"}, packetHandler = MBPacketHandlerClient.class))
public class MetallurgyBees {

	@Instance(MBReference.MOD_ID)
	public static MetallurgyBees instance;

	@SidedProxy(clientSide = MBReference.CLIENT_PROXY_CLASS, serverSide = MBReference.SERVER_PROXY_CLASS)
	public static MBCommonProxy proxy;

	public static MBConfig config;

	public static MBCreativeTabForestry creativeTab;

	public static Block beehive;

	public static Item honeyComb;
	public static Item hiveFrame;

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

		materialBeeHive = new MaterialBeeHive(true);
		// init blocks
		beehive = new BlockBeehive(MBConfig.blockBeehiveID).setUnlocalizedName("metallurgyBeehive");

		// register blocks
		GameRegistry.registerBlock(beehive, ItemBlockExtendedMetadata.class, "metallurgyBeehive");

		// init items
		honeyComb = new ItemHoneyComb(MBConfig.itemHoneyCombID).setUnlocalizedName("metallurgyHoneyComb");
		hiveFrame = new ItemHiveFrame(MBConfig.itemHiveFrameID).setUnlocalizedName("metallurgyFrame");

		// register items
		GameRegistry.registerItem(honeyComb, "metallurgyHoneyComb");
		GameRegistry.registerItem(hiveFrame, "metallurgyFrame");

		// register tileentities
		GameRegistry.registerTileEntity(TileEntityExtended.class, "MetallurgyTileExtended");
		GameRegistry.registerTileEntity(TileEntityMetadata.class, "MetallurgyTileMetadata");

		// set block harvest levels
		for(int i = 0; i < MetallurgyBeeTypes.values().length; i++) {
			Metal metal = Metals.getMetal(MetallurgyBeeTypes.values()[i].name);
			if(metal != null) {
				ItemStack ore = metal.oreInfo.getOre();
				MinecraftForge.setBlockHarvestLevel(beehive, i, "pickaxe", MinecraftForge.getBlockHarvestLevel(Block.blocksList[ore.itemID], ore.getItemDamage(), "pickaxe"));
			}
		}

		// add localizations to Forestry's Localization
		// Localization.instance.addLocalization("/assets/metallurgybees/lang/");
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

		// init bee branches
		branchMetal = new BranchBees();
		AlleleManager.alleleRegistry.getClassification("family.apidae").addMemberGroup(branchMetal);

		for(int i = 0; i < MetallurgyBeeTypes.values().length; i++) {
			MetallurgyBeeTypes beeType = MetallurgyBeeTypes.values()[i];
			beeType.metal = Metals.getMetal(beeType.name);
			beeType.hasHive = beeType.metal.oreInfo.getType() != OreType.ALLOY;

			// init bee species alleles
			beeType.speciesRough = new AlleleBeeSpecies(beeType.name + "Rough", true, "metallurgy.bees." + beeType.name + ".rough", branchMetal, "metallum", beeType.colorBeeRoughPrimary, beeType.colorBeeRoughSecondary).addProduct(new ItemStack(honeyComb.itemID, 1, i), 30);
			beeType.speciesRefined = new AlleleBeeSpecies(beeType.name + "Refined", true, "metallurgy.bees." + beeType.name + ".refined", branchMetal, "metallum", beeType.colorBeeRefinedPrimary, beeType.colorBeeRefinedSecondary).addProduct(new ItemStack(honeyComb.itemID, 1, i), 50);
			beeType.speciesReforged = new AlleleBeeSpecies(beeType.name + "Reforged", true, "metallurgy.bees." + beeType.name + ".reforged", branchMetal, "metallum", beeType.colorBeeReforgedPrimary, beeType.colorBeeReforgedSecondary).addProduct(new ItemStack(honeyComb.itemID, 1, i), 70);

			// register templates
			beeRoot.registerTemplate(getMetalBeeRoughTemplate(beeType));
			beeRoot.registerTemplate(getMetalBeeRefinedTemplate(beeType));
			beeRoot.registerTemplate(getMetalBeeReforgedTemplate(beeType));

			// init bee mutations
			if(beeType.hasHive) {
				new BeeMutation(beeType.speciesRough, AlleleManager.alleleRegistry.getAllele("forestry.speciesUnweary"), getMetalBeeRefinedTemplate(beeType), 5);
				new BeeMutation(beeType.speciesRefined, AlleleManager.alleleRegistry.getAllele("forestry.speciesIndustrious"), getMetalBeeReforgedTemplate(beeType), 2);
			}

			// register centrifuge recipes
			RecipeManagers.centrifugeManager.addRecipe(20, new ItemStack(honeyComb.itemID, 1, i), new ItemStack[]{Metals.getMetal(beeType.name).oreInfo.getDust(), ItemInterface.getItem("beeswax"), ItemInterface.getItem("honeyDrop")}, new int[]{25, 50, 25});

			// add hives and their drops
			if(beeType.hasHive) {
				beeType.hiveDrops.add(new HiveDrop(getMetalBeeRoughTemplate(beeType), new ItemStack[]{new ItemStack(honeyComb.itemID, 1, i)}, 80));

				GameRegistry.registerWorldGenerator(new WorldGenBeehives(beeType));
			}
		}

		// create alloy bee mutations
		createAlloyBeeMutations();
	}

	public void createAlloyBeeMutations() {
		createMutations(MetallurgyBeeTypes.COPPER.speciesRough, MetallurgyBeeTypes.TIN.speciesRough, MetallurgyBeeTypes.BRONZE);
		createMutations(MetallurgyBeeTypes.BRONZE.speciesRough, MetallurgyBeeTypes.GOLD.speciesRough, MetallurgyBeeTypes.HEPATIZON);
		createMutations(MetallurgyBeeTypes.BRONZE.speciesRough, MetallurgyBeeTypes.IRON.speciesRough, MetallurgyBeeTypes.DAMASCUS_STEEL);
		createMutations(MetallurgyBeeTypes.IRON.speciesRough, MetallurgyBeeTypes.GOLD.speciesRough, MetallurgyBeeTypes.ANGMALLEN);
		createMutations(MetallurgyBeeTypes.IRON.speciesRough, MetallurgyBeeTypes.MANGANESE.speciesRough, MetallurgyBeeTypes.STEEL);
		createMutations(MetallurgyBeeTypes.ZINC.speciesRough, MetallurgyBeeTypes.COPPER.speciesRough, MetallurgyBeeTypes.BRASS);
		createMutations(MetallurgyBeeTypes.GOLD.speciesRough, MetallurgyBeeTypes.SILVER.speciesRough, MetallurgyBeeTypes.ELECTRUM);
		createMutations(MetallurgyBeeTypes.LEMURITE.speciesRough, MetallurgyBeeTypes.SHADOW_IRON.speciesRough, MetallurgyBeeTypes.SHADOW_STEEL);
	}

	public void createMutations(IAlleleBeeSpecies parent1, IAlleleBeeSpecies parent2, MetallurgyBeeTypes child) {
		new BeeMutation(parent1, parent2, getMetalBeeRoughTemplate(child), 10);
		new BeeMutation(child.speciesRough, AlleleManager.alleleRegistry.getAllele("forestry.speciesUnweary"), getMetalBeeRefinedTemplate(child), 5);
		new BeeMutation(child.speciesRefined, AlleleManager.alleleRegistry.getAllele("forestry.speciesIndustrious"), getMetalBeeReforgedTemplate(child), 2);
	}

	public IAllele[] getDefaultMetalBeeTemplate() {
		IAllele[] alleles = beeRoot.getDefaultTemplate();
		alleles[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = alleleFlowerStone;
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

	@ForgeSubscribe
	public void onBlockBreak(BreakEvent event) {
		if(event.block.blockID == beehive.blockID) {
			event.setCanceled(true);
			ItemStack stack = event.getPlayer().getCurrentEquippedItem();
			if(stack != null && stack.getItem().onBlockStartBreak(stack, event.x, event.y, event.z, event.getPlayer())) {
				return;
			}
			int id = event.world.getBlockId(event.x, event.y, event.z);
			int meta = ((BlockExtendedMetadata) event.block).getMetadata(event.world, event.x, event.y, event.z);
			event.world.playAuxSFXAtEntity(event.getPlayer(), 2001, event.x, event.y, event.z, id);
			boolean flag = false;
			if(event.getPlayer().capabilities.isCreativeMode) {
				flag = removeBlock(event.world, event.x, event.y, event.z, meta, event.getPlayer());
				((EntityPlayerMP) event.getPlayer()).playerNetServerHandler.sendPacketToPlayer(new Packet53BlockChange(event.x, event.y, event.z, event.world));
			} else {
				ItemStack itemstack = event.getPlayer().getCurrentEquippedItem();
				boolean flag1 = false;
				Block block = Block.blocksList[id];
				if(block != null) {
					flag1 = block.canHarvestBlock(event.getPlayer(), meta);
				}
				if(itemstack != null) {
					itemstack.onBlockDestroyed(event.world, id, event.x, event.y, event.z, event.getPlayer());
					if(itemstack.stackSize == 0) {
						event.getPlayer().destroyCurrentEquippedItem();
					}
				}
				flag = removeBlock(event.world, event.x, event.y, event.z, meta, event.getPlayer());
				if(flag && flag1) {
					Block.blocksList[id].harvestBlock(event.world, event.getPlayer(), event.x, event.y, event.z, meta);
				}
			}
			if(!event.getPlayer().capabilities.isCreativeMode && flag && event != null) {
				Block.blocksList[id].dropXpOnBlockBreak(event.world, event.x, event.y, event.z, event.getExpToDrop());
			}
		}
	}

	private boolean removeBlock(World world, int x, int y, int z, int meta, EntityPlayer player) {
		Block block = Block.blocksList[world.getBlockId(x, y, z)];
		if(block != null) {
			block.onBlockHarvested(world, x, y, z, meta, player);
		}
		boolean flag = (block != null && block.removeBlockByPlayer(world, player, x, y, z));
		if(block != null && flag) {
			block.onBlockDestroyedByPlayer(world, x, y, z, meta);
		}
		return flag;
	}
}
