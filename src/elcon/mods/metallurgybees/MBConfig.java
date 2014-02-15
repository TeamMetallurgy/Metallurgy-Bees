package elcon.mods.metallurgybees;

import java.io.File;

import net.minecraftforge.common.Configuration;
import elcon.mods.metallurgybees.types.MetallurgyFrameTypes;

public class MBConfig {

	public Configuration config;

	public static int blockBeehiveID = 1002;

	public static int itemHoneyCombID = 6000;

	public static int itemFrameReinforcedID = 6001;
	public static int itemFrameFortifiedID = 6002;
	public static int itemFrameMidas = 6003;
	public static int itemFrameMutatingID = 6004;
	public static int itemFrameFertileID = 6005;
	public static int itemFrameAncientID = 6006;
	public static int itemFrameImmortalID = 6007;
	public static int itemFrameMoltenID = 6008;
	public static int itemFrameSanguineID = 6009;

	public static int itemBeeGunID = 6010;

	public MBConfig(Configuration config) {
		this.config = config;
	}

	public MBConfig(File file) {
		this(new Configuration(file));
	}

	public void load() {
		config.load();

		// load block ids
		blockBeehiveID = config.getBlock("beehive", blockBeehiveID).getInt();

		// load item ids
		itemHoneyCombID = config.getItem("honeyComb", itemHoneyCombID).getInt();

		itemFrameReinforcedID = config.getItem("frameReinforced", itemFrameReinforcedID).getInt();
		itemFrameFortifiedID = config.getItem("frameFortified", itemFrameFortifiedID).getInt();
		itemFrameMidas = config.getItem("frameMidas", itemFrameMidas).getInt();
		itemFrameMutatingID = config.getItem("frameMutating", itemFrameMutatingID).getInt();
		itemFrameFertileID = config.getItem("frameFertile", itemFrameFertileID).getInt();
		itemFrameAncientID = config.getItem("frameAncient", itemFrameAncientID).getInt();
		itemFrameImmortalID = config.getItem("frameImmortal", itemFrameImmortalID).getInt();
		itemFrameMoltenID = config.getItem("frameMolten", itemFrameMoltenID).getInt();
		itemFrameSanguineID = config.getItem("frameSanguine", itemFrameSanguineID).getInt();

		itemBeeGunID = config.getItem("beeGun", itemBeeGunID).getInt();
	}

	public void save() {
		config.save();
	}

	public int getFrameID(MetallurgyFrameTypes frames) {
		switch(frames) {
		case ANCIENT:
			return itemFrameAncientID;
		case FERTILE:
			return itemFrameFertileID;
		case FORTIFIED:
			return itemFrameFortifiedID;
		case IMMORTAL:
			return itemFrameImmortalID;
		case MIDAS:
			return itemFrameMidas;
		case MOLTEN:
			return itemFrameMoltenID;
		case MUTATING:
			return itemFrameMutatingID;
		case REINFORCED:
			return itemFrameReinforcedID;
		case SANGUINE:
			return itemFrameSanguineID;
		}
		return 0;
	}
}
