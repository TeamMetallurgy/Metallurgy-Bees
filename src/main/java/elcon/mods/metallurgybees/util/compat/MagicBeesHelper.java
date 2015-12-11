package elcon.mods.metallurgybees.util.compat;

import cpw.mods.fml.common.Loader;
import elcon.mods.metallurgybees.MBConfig;

public class MagicBeesHelper implements IModHelper {
	
	private static boolean isMBPresent = false;

	public static boolean isActive() {
		return isMBPresent;
	}

	public void preInit() {
		if (Loader.isModLoaded("MagicBees")) {
			isMBPresent = true;
		}
	}

	public void init() {
	}

	public void postInit() {
	}
}