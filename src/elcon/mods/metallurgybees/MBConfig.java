package elcon.mods.metallurgybees;

import java.io.File;

import net.minecraftforge.common.Configuration;

public class MBConfig {

	public Configuration config;
	
	public static int blockBeehiveID = 1500;
	
	public static int itemHoneyCombID = 6000;
	public static int itemHiveFrameID = 6001;

	public MBConfig(Configuration config) {
		this.config = config;
	}

	public MBConfig(File file) {
		this(new Configuration(file));
	}

	public void load() {
		config.load();
		
		//load block ids
		blockBeehiveID = config.getBlock("beehive", blockBeehiveID).getInt();
		
		//load item ids
		itemHoneyCombID = config.getItem("honeyComb", itemHoneyCombID).getInt();
		itemHiveFrameID = config.getItem("hiveFrame", itemHiveFrameID).getInt();
	}

	public void save() {
		config.save();
	}
}
