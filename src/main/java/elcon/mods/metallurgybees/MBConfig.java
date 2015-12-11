package elcon.mods.metallurgybees;

import java.io.File;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import elcon.mods.metallurgybees.util.compat.MagicBeesHelper;
import net.minecraftforge.common.config.Configuration;

public class MBConfig {

	public Configuration config;
	
	public static boolean magicBeesActive = false;
	public static boolean enableIgnobleBees = false;
	public static boolean enableSpecialBeesMutation = false;

	public MBConfig(Configuration config) {
		this.config = config;
		syncConfigs();
	}

	public MBConfig(File file) {
		this(new Configuration(file));
	}
	
	private boolean configBoolean(String category, String name, String tooltip, boolean requireRestart, boolean def) {
		return config.get(category, name, def, tooltip).getBoolean(def);
	}

	private boolean configBoolean(String category, String name, boolean requireRestart, boolean def) {
		return configBoolean(category, name, null, requireRestart, def);
	}

	private void syncConfigs() {
		enableIgnobleBees = configBoolean(Configuration.CATEGORY_GENERAL, "IgnobleBees", true, enableIgnobleBees);
		enableSpecialBeesMutation = configBoolean(Configuration.CATEGORY_GENERAL, "SpecialBeesMutation", true, enableSpecialBeesMutation);
		
		magicBeesActive = configBoolean("Mod Compatibility", "MagicBees", MagicBeesHelper.isActive(), magicBeesActive);

		if (config.hasChanged()){
			config.save();
		}
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if (MBReference.MOD_ID.equals(eventArgs.modID))
			syncConfigs();
	}
}
