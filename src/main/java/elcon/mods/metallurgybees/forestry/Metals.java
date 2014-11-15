package elcon.mods.metallurgybees.forestry;

import com.teammetallurgy.metallurgy.api.IMetalSet;
import com.teammetallurgy.metallurgy.api.MetallurgyApi;
import elcon.mods.metallurgybees.util.MBUtil;

import java.util.HashMap;

public class Metals {

    public static HashMap<String, Metal> metals = new HashMap<String, Metal>();

    public static Metal getMetal(String metal) {
        return metals.get(MBUtil.firstUpperCase(metal.replaceAll(" ", "_")));
    }

    public static void registerMetal(Metal metal) {
        metals.put(metal.name, metal);
    }

    public static void unRegisterMetal(String metal) {
        metals.remove(metal.toLowerCase());
    }

    public static void init() {
        for (String setName : MetallurgyApi.getSetNames()) {
            IMetalSet metalSet = MetallurgyApi.getMetalSet(setName);

            for (String metalNames : metalSet.getMetalNames()) {
                registerMetal(new Metal(metalNames.replaceAll(" ", "_"), setName, metalSet));
            }
        }
    }

    public static class Metal {

        public String name;
        public String setName;
        public IMetalSet ore;

        public Metal(String name, String setName, IMetalSet ore) {
            this.name = name;
            this.setName = setName;
            this.ore = ore;
        }
    }
}
