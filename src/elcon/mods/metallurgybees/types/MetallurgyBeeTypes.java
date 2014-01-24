package elcon.mods.metallurgybees.types;

import java.util.ArrayList;

import net.minecraft.util.Icon;
import elcon.mods.metallurgybees.Metals;
import elcon.mods.metallurgybees.Metals.Metal;
import elcon.mods.metallurgybees.util.ColorPair;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IHiveDrop;

public enum MetallurgyBeeTypes {

	COPPER("copper", 				new ColorPair(192, 81, 14, 137, 57, 10), 		new ColorPair(238, 104, 23, 192, 81, 14), 		new ColorPair(241, 136, 73, 238, 104, 23), 		new ColorPair(241, 136, 73, 192, 81, 14)),
	TIN("tin", 						new ColorPair(170, 170, 170, 126, 126, 126), 	new ColorPair(192, 192, 192, 170, 170, 170), 	new ColorPair(210, 210, 210, 192, 192, 192), 	new ColorPair(210, 210, 210, 170, 170, 170)),
	BRONZE("bronze",				new ColorPair(132, 80, 32, 94, 56, 23), 		new ColorPair(186, 112, 44, 132, 80, 32), 		new ColorPair(225, 175, 130, 186, 112, 44),		new ColorPair(225, 175, 130, 132, 80, 32)),
	IRON("iron", 					new ColorPair(175, 142, 119, 130, 98, 77), 		new ColorPair(216, 175, 147, 175, 142, 119),	new ColorPair(235, 210, 194, 216, 175, 147), 	new ColorPair(235, 210, 194, 188, 153, 128)),
	HEPATIZON("hepatizon", 			new ColorPair(97, 78, 97, 73, 58, 73),			new ColorPair(148, 122, 148, 97, 78, 97), 		new ColorPair(180, 160, 180, 148, 122, 148), 	new ColorPair(180, 160, 180, 97, 78, 97)),
	DAMASCUS_STEEL("damascusSteel", new ColorPair(88, 63, 44, 65, 47, 33), 			new ColorPair(153, 109, 77, 88, 63, 44), 		new ColorPair(223, 205, 191, 153, 109, 77), 	new ColorPair(223, 205, 191, 153, 109, 77)),
	ANGMALLEN("angmallen",			new ColorPair(207, 190, 71,156, 142, 41), 		new ColorPair(225, 215, 138, 207, 190, 71), 	new ColorPair(235, 227, 177, 225, 215, 138), 	new ColorPair(235, 227, 177, 207, 190, 71)),
	MANGANESE("manganese", 			new ColorPair(255, 136, 136, 255, 66, 66), 		new ColorPair(255, 187, 187, 255, 136, 136), 	new ColorPair(255, 221, 221, 255, 187, 187), 	new ColorPair(255, 221, 221, 255, 136, 136)),
	STEEL("steel", 					new ColorPair(136, 136, 136, 104, 104, 104), 	new ColorPair(173, 173, 173, 136, 136, 136), 	new ColorPair(216, 216, 216, 173, 173, 173), 	new ColorPair(216, 216, 216, 104, 104, 104)),
	ZINC("zinc", 					new ColorPair(147, 152, 54, 112, 115, 40), 		new ColorPair(191, 197, 92, 147, 152, 54), 		new ColorPair(220, 223, 164, 191, 197, 92), 	new ColorPair(220, 223, 164, 147, 152, 54)),
	BRASS("brass", 					new ColorPair(101, 69, 20, 62, 43, 13), 		new ColorPair(216, 150, 52, 101, 69, 20), 		new ColorPair(236, 205, 159, 216, 150, 52), 	new ColorPair(236, 205, 159, 101, 69, 20)),
	SILVER("silver", 				new ColorPair(173, 173, 173, 102, 102, 102), 	new ColorPair(212, 212, 212, 173, 173, 173), 	new ColorPair(229, 229, 229, 212, 212, 212), 	new ColorPair(229, 229, 229, 173, 173, 173)),
	GOLD("gold",					new ColorPair(248, 175, 43, 183, 97, 16), 		new ColorPair(252, 238, 75, 248, 175, 43), 		new ColorPair(255, 255, 181, 250, 190, 80), 	new ColorPair(255, 255, 181, 183, 97, 16)),
	ELECTRUM("electrum", 			new ColorPair(185, 153, 70, 100, 83, 38), 		new ColorPair(206, 184, 127, 185, 153, 70), 	new ColorPair(223, 208, 170, 206, 184, 127), 	new ColorPair(223, 208, 170, 185, 153, 70)),
	PLATINUM("platinum", 			new ColorPair(54, 103, 112, 38, 72, 79), 		new ColorPair(155, 201, 208, 54, 103, 112), 	new ColorPair(220, 236, 239, 155, 201, 208), 	new ColorPair(220, 236, 239, 54, 103, 112)),
	IGNATIUS("ignatius", 			new ColorPair(255, 128, 0, 176, 88, 0), 	    new ColorPair(255, 169, 83, 255, 128, 0),	    new ColorPair(255, 206, 157, 255, 169, 83), 	new ColorPair(255, 169, 83, 176, 88, 0)),
	SHADOW_IRON("shadowIron", 		new ColorPair(82, 62, 49, 56, 43, 33), 	        new ColorPair(141, 117, 101, 82, 62, 49), 	    new ColorPair(188, 171, 160, 141, 117, 101), 	new ColorPair(188, 171, 160, 82, 62, 49)),
	LEMURITE("lemurite", 			new ColorPair(176, 176, 176, 131, 131, 131), 	new ColorPair(219, 219, 219, 176, 176, 176), 	new ColorPair(254, 255, 255, 219, 219, 219), 	new ColorPair(219, 219, 219, 131, 131, 131)),
	SHADOW_STEEL("shadowSteel", 	new ColorPair(97, 82, 69, 58, 50, 41), 			new ColorPair(136, 115, 98, 97, 82, 69), 		new ColorPair(235, 230, 226, 199, 185, 175), 	new ColorPair(199, 185, 175, 97, 82, 69)),
	MIDASIUM("midasium", 			new ColorPair(242, 146, 0, 109, 58, 1), 		new ColorPair(255, 203, 125, 242, 146, 0), 		new ColorPair(255, 231, 193, 255, 268, 38), 	new ColorPair(255, 203, 125, 255, 268, 38)),
	VYROXERES("vyroxeres", 			new ColorPair(55, 146, 1, 34, 88, 1), 			new ColorPair(136, 254, 65, 55, 146, 1), 		new ColorPair(211, 255, 185, 85, 224, 1), 		new ColorPair(85, 224, 1, 55, 146, 1)),
	CERUCLASE("ceruclase", 			new ColorPair(69, 143, 171, 29, 60, 71), 		new ColorPair(140, 189, 208, 54, 112, 133), 	new ColorPair(191, 219, 230, 140, 189, 208), 	new ColorPair(140, 189, 208, 54, 112, 133)),
	ALDUORITE("alduorite", 			new ColorPair(163, 222, 222, 29, 78, 78), 		new ColorPair(213, 240, 240, 53, 145, 145), 	new ColorPair(241, 250, 250, 163, 222, 222), 	new ColorPair(163, 222, 222, 53, 145, 145)),
	INOLASHITE("inolashite",		new ColorPair(64, 170, 125, 30, 79, 57), 		new ColorPair(148, 216, 187, 44, 116, 85), 		new ColorPair(199, 235, 220, 64, 170, 125), 	new ColorPair(148, 216, 187, 30, 79, 57)),
	KALENDRITE("kalendrite",		new ColorPair(170, 91, 189, 98, 45, 111), 		new ColorPair(198, 145, 210, 122, 56, 139), 	new ColorPair(222, 193, 230, 170, 91, 189), 	new ColorPair(222, 193, 230, 122, 56, 139)),
	AMORDRINE("amordrine", 			new ColorPair(169, 141, 177, 113, 83, 121), 	new ColorPair(210, 196, 215, 143, 107, 154), 	new ColorPair(231, 223, 234, 169, 141, 177), 	new ColorPair(231, 223, 234, 143, 107, 154)),
	VULCANITE("vulcanite", 			new ColorPair(255, 132, 72, 204, 66, 0), 		new ColorPair(255, 176, 138, 242, 79, 0), 		new ColorPair(255, 209, 187, 255, 132, 72), 	new ColorPair(255, 209, 187, 255, 132, 72)),
	SANGUINITE("sanguinite", 		new ColorPair(185, 0, 0, 130, 0, 0), 			new ColorPair(255, 15, 15, 185, 0, 0), 			new ColorPair(255, 125, 125, 255, 15, 15), 		new ColorPair(255, 125, 125, 185, 0, 0)),
	
	PROMETHEUM("prometheum", 		new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255)),
	DEEP_IRON("deepIron", 			new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255)),
	INFUSCOLIUM("infuscolium", 		new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255)),
	BLACK_STEEL("blackSteel", 		new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255)),
	OURECLASE("oureclase", 			new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255)),
	ASTRAL_SILVER("astralSilver", 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255)),
	CARMOT("carmot", 				new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255)),
	MITHRIL("mithril", 				new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255)),
	RUBRACIUM("rubracium", 			new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255)),
	QUICKSILVER("quicksilver", 		new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255)),
	HADEROTH("haderoth", 			new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255),	new ColorPair(255, 255, 255, 255, 255, 255)),
	ORICHALCUM("orichalcum", 		new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255),	new ColorPair(255, 255, 255, 255, 255, 255)),
	CELENEGIL("celenegil", 			new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255)),
	ADAMANTINE("adamantine",		new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255)),
	ATLARUS("atlarus", 				new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255)),
	TARTARITE("tartarite", 			new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255)),
	PHOSPHORITE("phosphorite", 		new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255)),
	SULFUR("sulfur", 				new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255)),
	SALTPETER("saltpeter", 			new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255)),
	MAGNESIUM("magnesium", 			new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255)),
	BITUMEN("bitumen", 				new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255)),
	POTASH("potash", 				new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255)),
	EXIMITE("eximite", 				new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255)),
	MEUTOITE("meutoite", 			new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255)),
	DESICHALKOS("desichalkos", 		new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(255, 255, 255, 255, 255, 255));
	
	MetallurgyBeeTypes(String name, ColorPair rough, ColorPair refined, ColorPair reforged , ColorPair comb) {
		this.name = name;
		this.colorBeeRoughPrimary = rough.getRGB1();
		this.colorBeeRoughSecondary = rough.getRGB2();
		this.colorBeeRefinedPrimary = refined.getRGB1();
		this.colorBeeRefinedSecondary = refined.getRGB2();
		this.colorBeeReforgedPrimary = reforged.getRGB1();
		this.colorBeeReforgedSecondary = reforged.getRGB2();
		this.colorCombPrimary = comb.getRGB2();
		this.colorCombSecondary = comb.getRGB1();
	}	
	
	public String name;
	public Metal metal;
	public int colorBeeRoughPrimary;
	public int colorBeeRoughSecondary;
	public int colorBeeRefinedPrimary;
	public int colorBeeRefinedSecondary;
	public int colorBeeReforgedPrimary;
	public int colorBeeReforgedSecondary;
	public int colorCombPrimary;
	public int colorCombSecondary;
	
	public boolean hasHive;
	
	public ArrayList<IHiveDrop> hiveDrops = new ArrayList<IHiveDrop>();
	
	public Icon iconBeehiveTop;
	public Icon iconBeehiveSide;
	
	public IAlleleBeeSpecies speciesRough;
	public IAlleleBeeSpecies speciesRefined;
	public IAlleleBeeSpecies speciesReforged;
}
