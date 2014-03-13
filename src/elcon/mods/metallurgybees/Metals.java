package elcon.mods.metallurgybees;

import java.lang.reflect.Field;
import java.util.HashMap;

import rebelkeithy.mods.metallurgy.api.IMetalSet;
import rebelkeithy.mods.metallurgy.api.IOreInfo;
import rebelkeithy.mods.metallurgy.api.MetallurgyAPI;

public class Metals {

	public static class Metal {

		public String name;
		public String setName;
		public IOreInfo oreInfo;

		public Metal(String name, String setName, IOreInfo oreInfo) {
			this.name = name;
			this.setName = setName;
			this.oreInfo = oreInfo;
		}
	}

	public static HashMap<String, Metal> metals = new HashMap<String, Metal>();

	public static Metal getMetal(String metal) {
		return metals.get(metal.toLowerCase());
	}

	public static void registerMetal(Metal metal) {
		metals.put(metal.name.toLowerCase(), metal);
	}

	public static void unregisterMetal(String metal) {
		metals.remove(metal.toLowerCase());
	}

	public static void init() {
		for(int i = 0; i < MetallurgyAPI.getMetalSetNames().length; i++) {
			IMetalSet metalSet = MetallurgyAPI.getMetalSet(MetallurgyAPI.getMetalSetNames()[i]);
			for(IOreInfo oreInfo : metalSet.getOreList().values()) {
				registerMetal(new Metal(oreInfo.getName().replaceAll(" ", ""), MetallurgyAPI.getMetalSetNames()[i], oreInfo));
			}
		}
		
		try {
			Class<?> metallurgyMetals = Class.forName("rebelkeithy.mods.metallurgy.vanilla.MetallurgyVanilla");

			final Field set = metallurgyMetals.getField("vanillaSet");
			IMetalSet metalSet = (IMetalSet) set.get(null);

			for(IOreInfo oreInfo : metalSet.getOreList().values()) {
				registerMetal(new Metal(oreInfo.getName().replaceAll(" ", ""), "Vanilla", oreInfo));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
