package elcon.mods.metallurgybees.util;

import net.minecraftforge.common.config.Configuration;

import java.io.File;


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
