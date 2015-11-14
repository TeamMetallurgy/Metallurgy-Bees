package elcon.mods.metallurgybees;

import java.io.File;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

public class MBConfig {

	public Configuration config;

	public MBConfig(Configuration config) {
		this.config = config;
		syncConfigs();
	}

	public MBConfig(File file) {
		this(new Configuration(file));
	}
	
	private boolean configBoolean(String name, String tooltip, boolean requireRestart, boolean def) {
		return config.get(Configuration.CATEGORY_GENERAL, name, def, tooltip).getBoolean(def);
	}

	private boolean configBoolean(String name, boolean requireRestart, boolean def) {
		return configBoolean(name, null, requireRestart, def);
	}

	private void syncConfigs() {
		MetallurgyBees.enableIgnobleBees = configBoolean("IgnobleBees", true, MetallurgyBees.enableIgnobleBees);
		MetallurgyBees.enableSpecialBeesMutation = configBoolean("SpecialBeesMutation", true, MetallurgyBees.enableSpecialBeesMutation);

		if (config.hasChanged())
			config.save();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if (MBReference.MOD_ID.equals(eventArgs.modID))
			syncConfigs();
	}
}
