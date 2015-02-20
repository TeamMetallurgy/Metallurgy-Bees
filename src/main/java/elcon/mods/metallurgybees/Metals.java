package elcon.mods.metallurgybees;

import java.util.HashMap;

import com.teammetallurgy.metallurgy.api.IMetalInfo;
import com.teammetallurgy.metallurgy.api.IMetalSet;
import com.teammetallurgy.metallurgy.api.MetallurgyApi;

public class Metals {

	public static class Metal {

		public String name;
		public String setName;
		// TODO: Rename to metalInfo
		public IMetalInfo oreInfo;
		public IMetalSet metalSet;

		public Metal(String name, String setName, IMetalInfo metalInfo, IMetalSet metalSet) {
			this.name = name;
			this.setName = setName;
			this.oreInfo = metalInfo;
			this.metalSet = metalSet;
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
		String [] setNames = MetallurgyApi.getSetNames();
		for(int i = 0; i < setNames.length; i++) {
			IMetalSet metalSet = MetallurgyApi.getMetalSet(setNames[i]);
			for(String metalName : metalSet.getMetalNames()) {
				registerMetal(new Metal(metalName.replaceAll(" ", ""), setNames[i], metalSet.getMetal(metalName), metalSet));
			}
		}
	}
}
