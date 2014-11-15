package elcon.mods.metallurgybees.util;

import elcon.mods.metallurgybees.types.MetallurgyBeeTypes;

public class MBUtil {

    public static String firstUpperCase(String s) {
        return Character.toString(s.charAt(0)).toUpperCase() + s.substring(1, s.length()).replace(" ", "");
    }

    public static String getBeeParent(MetallurgyBeeTypes types, int parent) {
        if (parent == 1) {
            if (types.metal.setName.equalsIgnoreCase("base")) {
                return "forestry.speciesUnweary";
            }
            if (types.metal.setName.equalsIgnoreCase("precious")) {
                return "forestry.speciesMajestic";

            } else if (types.metal.setName.equalsIgnoreCase("nether")) {
                return "forestry.speciesFiendish";

            }
            if (types.metal.setName.equalsIgnoreCase("fantasy")) {
                return "forestry.speciesValiant";

            }
            if (types.metal.setName.equalsIgnoreCase("ender")) {
                return "forestry.speciesSpectral";

            }
            if (types.metal.setName.equalsIgnoreCase("utility")) {
                return "forestry.speciesRural";
            }
            if (types.name.equalsIgnoreCase("iron")) {
                return "forestry.speciesUnweary";

            }
            if (types.name.equalsIgnoreCase("gold")) {
                return "forestry.speciesMajestic";
            }
        } else {
            if (types.metal.setName.equalsIgnoreCase("base")) {
                return "forestry.speciesIndustrious";

            }
            if (types.metal.setName.equalsIgnoreCase("precious")) {
                return "forestry.speciesImperial";

            }
            if (types.metal.setName.equalsIgnoreCase("nether")) {
                return "forestry.speciesDemonic";

            }
            if (types.metal.setName.equalsIgnoreCase("fantasy")) {
                return "forestry.speciesHeroic";

            }
            if (types.metal.setName.equalsIgnoreCase("ender")) {
                return "forestry.speciesPhantasmal";

            }
            if (types.metal.setName.equalsIgnoreCase("utility")) {
                return "forestry.speciesRural";
            }
            if (types.name.equalsIgnoreCase("iron")) {
                return "forestry.speciesIndustrious";

            }
            if (types.name.equalsIgnoreCase("gold")) {
                return "forestry.speciesImperial";
            }
        }
        return "";
    }
}
