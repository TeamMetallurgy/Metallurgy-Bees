package elcon.mods.metallurgybees;

import java.util.HashMap;

import com.teammetallurgy.metallurgy.api.IMetalInfo;
import com.teammetallurgy.metallurgy.api.IMetalSet;
import com.teammetallurgy.metallurgy.api.MetallurgyApi;

import elcon.mods.metallurgybees.util.LogHelper;

public class Metals {

	public static class Metal {

		public String name;
		public String setName;
		public IMetalInfo metalInfo;
		public IMetalSet metalSet;

		public Metal(String name, String setName, IMetalInfo metalInfo, IMetalSet metalSet) {
			this.name = name;
			this.setName = setName;
			this.metalInfo = metalInfo;
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
		for(String setNames : MetallurgyApi.getSetNames()) {
			IMetalSet metalSet = MetallurgyApi.getMetalSet(setNames);
			for(String metalName : metalSet.getMetalNames()) {
				registerMetal(new Metal(metalName.replaceAll(" ", ""), setNames, metalSet.getMetal(metalName), metalSet));
			}
		}
	}
}
