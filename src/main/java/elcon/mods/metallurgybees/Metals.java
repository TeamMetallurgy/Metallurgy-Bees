package elcon.mods.metallurgybees;

import java.util.HashMap;

import com.teammetallurgy.metallurgy.BlockList;
import com.teammetallurgy.metallurgy.api.IMetalSet;
import com.teammetallurgy.metallurgy.metals.MetalSet;
import net.minecraft.item.ItemStack;

public class Metals {

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

    public static HashMap<String, Metal> metals = new HashMap<String, Metal>();

    public static Metal getMetal(String metal) {
        return metals.get(metal.toLowerCase());
    }

    public static void registerMetal(Metal metal) {
        metals.put(metal.name.toLowerCase(), metal);
    }

    public static void unRegisterMetal(String metal) {
        metals.remove(metal.toLowerCase());
    }

    public static void init() {

        for (String setName : BlockList.getLoadedSetNames()) {
            IMetalSet metalSet = BlockList.getSet(setName);

            for (String metalNames : metalSet.getMetalNames()) {
                registerMetal(new Metal(metalNames, setName, metalSet));
                // oreInfo.getName().replaceAll(" ", ""), MetallurgyAPI.getMetalSetNames()[i], oreInfo));
            }
        }
    }
}
