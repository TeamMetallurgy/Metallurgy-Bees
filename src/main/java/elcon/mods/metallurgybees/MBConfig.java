package elcon.mods.metallurgybees;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class MBConfig {

	public Configuration config;

	public MBConfig(Configuration config) {
		this.config = config;
	}

	public MBConfig(File file) {
		this(new Configuration(file));
	}

	public void load() {
		config.load();

		
	}

	public void save() {
		config.save();
	}

}
