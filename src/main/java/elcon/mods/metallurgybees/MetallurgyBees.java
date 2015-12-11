package elcon.mods.metallurgybees;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

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
import elcon.mods.metallurgybees.bees.AlleleBeeSpecies;
import elcon.mods.metallurgybees.bees.AlleleFlowers;
import elcon.mods.metallurgybees.bees.BeeManager;
import elcon.mods.metallurgybees.bees.BeeMutation;
import elcon.mods.metallurgybees.bees.BranchBees;
import elcon.mods.metallurgybees.blocks.BlockBeehive;
import elcon.mods.metallurgybees.blocks.BlockExtendedMetadata;
import elcon.mods.metallurgybees.blocks.MBBlocks;
import elcon.mods.metallurgybees.blocks.MaterialBeeHive;
import elcon.mods.metallurgybees.items.HiveDrop;
import elcon.mods.metallurgybees.items.ItemBeeGun;
import elcon.mods.metallurgybees.items.ItemBlockExtendedMetadata;
import elcon.mods.metallurgybees.items.ItemHiveFrame;
import elcon.mods.metallurgybees.items.ItemHoneyComb;
import elcon.mods.metallurgybees.items.MBItems;
import elcon.mods.metallurgybees.tileentities.TileEntityExtended;
import elcon.mods.metallurgybees.tileentities.TileEntityMetadata;
import elcon.mods.metallurgybees.types.MetallurgyBeeTypes;
import elcon.mods.metallurgybees.types.MetallurgyFrameTypes;
import elcon.mods.metallurgybees.util.LogHelper;
import elcon.mods.metallurgybees.util.compat.MagicBeesHelper;
import elcon.mods.metallurgybees.util.compat.ModHelperManager;
import elcon.mods.metallurgybees.worldgen.WorldGenBeehives;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IAlleleBeeEffect;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IClassification;
import forestry.api.recipes.RecipeManagers;
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

@Mod(modid = MBReference.MOD_ID, name = MBReference.NAME, version = MBReference.VERSION, acceptedMinecraftVersions = MBReference.MC_VERSION, dependencies = MBReference.DEPENDENCIES)
public class MetallurgyBees {

	@Instance(MBReference.MOD_ID)
	public static MetallurgyBees instance;

	@SidedProxy(clientSide = MBReference.CLIENT_PROXY_CLASS, serverSide = MBReference.SERVER_PROXY_CLASS)
	public static MBCommonProxy proxy;

	public static MBConfig config;

	public static MBCreativeTabForestry creativeTab;

	public static Material materialBeeHive;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		LogHelper.info("Preinit started");

		// Compatibility Helpers setup preInit.
		ModHelperManager.preInit();
				
		// init config
		config = new MBConfig(event.getSuggestedConfigurationFile());

		// init creative tab
		creativeTab = new MBCreativeTabForestry("MetallurgyAddonForestry");

		// init materials
		materialBeeHive = new MaterialBeeHive();

		// init and register blocks
		MBBlocks.initBlocks();

		// init and register items
		MBItems.initItems();

		// register tileentities
		GameRegistry.registerTileEntity(TileEntityExtended.class, "MetallurgyTileExtended");
		GameRegistry.registerTileEntity(TileEntityMetadata.class, "MetallurgyTileMetadata");

		LogHelper.info("Preinit completed");
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// Compatibility Helpers setup init.
		ModHelperManager.init();
		
		// register render information
		proxy.registerRenderingInformation();

		// register events
		MinecraftForge.EVENT_BUS.register(this);

		LogHelper.info("Init completed");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		// Compatibility Helpers setup postInit.
		ModHelperManager.postInit();
		
		// init metals
		Metals.init();
		
		BeeManager.getBeeRoot();
		BeeManager.setupAlleles();

		LogHelper.info("Postinit completed");
	}

	/*@SubscribeEvent
	public void onBlockBreak(BreakEvent event) {
		if(event.block != null) {
			if(beehives.containsValue(event.block)) {
				event.setCanceled(true);
				ItemStack stack = event.getPlayer().getCurrentEquippedItem();
				if(stack != null && stack.getItem().onBlockStartBreak(stack, event.x, event.y, event.z, event.getPlayer())) {
					return;
				}
				Block block = event.world.getBlock(event.x, event.y, event.z);
				int meta = event.world.getBlockMetadata(event.x, event.y, event.z);
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
	}*/

	/*private boolean removeBlock(World world, int x, int y, int z, int meta, EntityPlayer player, boolean drop) {
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
	}*/
}
