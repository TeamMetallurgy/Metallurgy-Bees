package elcon.mods.metallurgybees;


import com.teammetallurgy.metallurgy.api.MetalType;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.metallurgybees.blocks.BlockBeehive;
import elcon.mods.metallurgybees.blocks.ItemBlockBeehive;
import elcon.mods.metallurgybees.forestry.*;
import elcon.mods.metallurgybees.handler.ForgeEventHandler;
import elcon.mods.metallurgybees.items.HiveDrop;
import elcon.mods.metallurgybees.items.ItemHiveFrame;
import elcon.mods.metallurgybees.items.ItemHoneyComb;
import elcon.mods.metallurgybees.proxy.MBCommonProxy;
import elcon.mods.metallurgybees.types.MetallurgyBeeTypes;
import elcon.mods.metallurgybees.types.MetallurgyFrameTypes;
import elcon.mods.metallurgybees.util.MBConfig;
import elcon.mods.metallurgybees.util.MBCreativeTabForestry;
import elcon.mods.metallurgybees.util.MBReference;
import elcon.mods.metallurgybees.util.MBUtil;
import elcon.mods.metallurgybees.worldgen.WorldGenBeehives;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IClassification;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = MBReference.MOD_ID, name = MBReference.NAME, version = MBReference.VERSION, acceptedMinecraftVersions = MBReference.MC_VERSION, dependencies = MBReference.DEPENDENCIES)
public class MetallurgyBees {

    @Instance(MBReference.MOD_ID)
    public static MetallurgyBees instance;

    @SidedProxy(clientSide = MBReference.CLIENT_PROXY_CLASS, serverSide = MBReference.SERVER_PROXY_CLASS)
    public static MBCommonProxy proxy;

    public static MBCreativeTabForestry creativeTab = new MBCreativeTabForestry("MetallurgyAddonForestry");


    public static Item honeyComb;
    public static Item hiveFrame;

    public static IBeeRoot beeRoot;
    public static IClassification branchMetal;
    public static AlleleFlowers alleleFlowerStone;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // init config
        MBConfig config = new MBConfig(event.getSuggestedConfigurationFile());
        config.load();
        config.save();

        MetallurgyFrameTypes.init();

        // init items
        for (MetallurgyFrameTypes frame : MetallurgyFrameTypes.values()) {
            hiveFrame = new ItemHiveFrame(frame).setUnlocalizedName("metallurgyFrame." + frame.name());
            GameRegistry.registerItem(hiveFrame, "metallurgyFrame" + MBUtil.firstUpperCase(frame.name().toLowerCase()));
        }

        honeyComb = new ItemHoneyComb().setUnlocalizedName("metallurgyHoneyComb");
        GameRegistry.registerItem(honeyComb, "metallurgyHoneyComb");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        // register render information
        proxy.registerRenderingInformation();

        // register events
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        // init metals
        Metals.init();

        // get bee root
        beeRoot = (IBeeRoot) AlleleManager.alleleRegistry.getSpeciesRoot("rootBees");

        // init flower provider
        alleleFlowerStone = new AlleleFlowers();
        registerAllele(alleleFlowerStone);

        // init bee branches
        branchMetal = new BranchBees();
        AlleleManager.alleleRegistry.getClassification("family.apidae").addMemberGroup(branchMetal);

        for (MetallurgyBeeTypes beeType : MetallurgyBeeTypes.values()) {
            beeType.metal = Metals.getMetal(beeType.name);

            System.out.println(beeType.metal + ":" + beeType.name);

            beeType.speciesRough = new AlleleBeeSpecies("metallurgy.species." + beeType.name.replace(" ", "") + "Rough", true, "metallurgy.species." + beeType.name.replace(" ", "") + ".rough", branchMetal, "metallum", beeType.colorBeeRoughPrimary, beeType.colorBeeRoughSecondary).addProduct(new ItemStack(honeyComb, 1, beeType.ordinal()), 30);
            registerAllele(beeType.speciesRough);
            beeRoot.registerTemplate(getMetalBeeRoughTemplate(beeType));

            beeType.speciesReforged = new AlleleBeeSpecies("metallurgy.species." + beeType.name.replace(" ", "") + "Reforged", true, "metallurgy.species." + beeType.name.replace(" ", "") + ".reforged", branchMetal, "metallum", beeType.colorBeeReforgedPrimary, beeType.colorBeeReforgedSecondary).addProduct(new ItemStack(honeyComb, 1, beeType.ordinal()), 70);
            registerAllele(beeType.speciesReforged);
            beeRoot.registerTemplate(getMetalBeeReforgedTemplate(beeType));

            if (!beeType.metal.setName.equals("utility")) {
                beeType.speciesRefined = new AlleleBeeSpecies("metallurgy.species." + beeType.name.replace(" ", "") + "Refined", true, "metallurgy.species." + beeType.name.replace(" ", "") + ".refined", branchMetal, "metallum", beeType.colorBeeRefinedPrimary, beeType.colorBeeRefinedSecondary).addProduct(new ItemStack(honeyComb, 1, beeType.ordinal()), 50);
                registerAllele(beeType.speciesRefined);
                beeRoot.registerTemplate(getMetalBeeRefinedTemplate(beeType));
            }

            // init bee species alleles
            if (beeType.metal.setName.equals("nether")) {
                ((AlleleBeeSpecies) beeType.speciesRough).setTemperature(EnumTemperature.HELLISH);
                ((AlleleBeeSpecies) beeType.speciesRefined).setTemperature(EnumTemperature.HELLISH);
                ((AlleleBeeSpecies) beeType.speciesReforged).setTemperature(EnumTemperature.HELLISH);
            }


            // init bee mutations
            if (beeType.hasHive = beeType.metal.ore.getMetal(beeType.name).getType() != MetalType.Alloy) {
                // init blocks
                beeType.beeHive = new BlockBeehive(beeType.name);

                beeType.beeHive.setHarvestLevel("pickaxe", Metals.getMetal(beeType.name).ore.getMetal(beeType.name).getToolHarvestLevel());

                // register blocks
                GameRegistry.registerBlock(beeType.beeHive, ItemBlockBeehive.class, "metallurgyBeehive." + beeType.name);

                if (beeType.metal.setName.equals("utility")) {
                    new BeeMutation(beeType.speciesRough, getAllele(MBUtil.getBeeParent(beeType, 2)), getMetalBeeReforgedTemplate(beeType), 2);
                } else {
                    new BeeMutation(beeType.speciesRough, getAllele(MBUtil.getBeeParent(beeType, 1)), getMetalBeeRefinedTemplate(beeType), 5);
                    new BeeMutation(beeType.speciesRefined, getAllele(MBUtil.getBeeParent(beeType, 2)), getMetalBeeReforgedTemplate(beeType), 2);
                }

                beeType.hiveDrops.add(new HiveDrop(getMetalBeeRoughTemplate(beeType), new ItemStack[]{new ItemStack(honeyComb, 1, beeType.ordinal())}, 80));

                // add worldgen
                GameRegistry.registerWorldGenerator(new WorldGenBeehives(beeType), 0);
            }
            System.out.println(getMetalDust(beeType.name) + ":" + beeType.name);

            // register centrifuge recipes
            RecipeManagers.centrifugeManager.addRecipe(20, new ItemStack(honeyComb, 1, beeType.ordinal()), new ItemStack[]{getMetalDust(beeType.name), (OreDictionary.getOres("itemBeeswax").get(0)), (OreDictionary.getOres("dropHoney").get(0))}, new int[]{25, 50, 25});

        }
        // create alloy bee mutations
        createAlloyBeeMutations();
    }

    private IAllele getAllele(String beeParent) {
        return AlleleManager.alleleRegistry.getAllele(beeParent);
    }

    private void registerAllele(IAllele speciesRough) {
        AlleleManager.alleleRegistry.registerAllele(speciesRough);
    }

    public void createAlloyBeeMutations() {
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
    }

    public void createMutations(MetallurgyBeeTypes parent1, MetallurgyBeeTypes parent2, MetallurgyBeeTypes child) {
        new BeeMutation(parent1.speciesRough, parent2.speciesRough, getMetalBeeRoughTemplate(child), 100);
        new BeeMutation(child.speciesRough, AlleleManager.alleleRegistry.getAllele(MBUtil.getBeeParent(child, 1)), getMetalBeeRefinedTemplate(child), 5);
        new BeeMutation(child.speciesRefined, AlleleManager.alleleRegistry.getAllele(MBUtil.getBeeParent(child, 2)), getMetalBeeReforgedTemplate(child), 2);
    }


    public ItemStack getMetalDust(String beeType) {
        if (beeType.equalsIgnoreCase("iron")) {
            return OreDictionary.getOres("dustIron").get(0);
        }
        if (beeType.equalsIgnoreCase("gold")) {
            return OreDictionary.getOres("dustGold").get(0);
        }
        if (Metals.getMetal(beeType).setName.equalsIgnoreCase("Utility")) {
            ItemStack drop = Metals.getMetal(beeType).ore.getDrop(beeType).copy();
            drop.stackSize = 1;
            return drop;
        }
        return Metals.getMetal(beeType).ore.getDust(MBUtil.firstUpperCase(beeType.replaceAll("_", " ")));
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
}
