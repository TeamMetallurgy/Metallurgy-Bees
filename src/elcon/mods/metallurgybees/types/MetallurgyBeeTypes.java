package elcon.mods.metallurgybees.types;

import java.util.ArrayList;

import net.minecraft.util.Icon;
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
	MIDASIUM("midasium", 			new ColorPair(242, 146, 0, 109, 58, 1), 		new ColorPair(255, 203, 125, 242, 146, 0), 		new ColorPair(255, 231, 193, 255, 168, 38), 	new ColorPair(255, 203, 125, 255, 168, 38)),
	VYROXERES("vyroxeres", 			new ColorPair(55, 146, 1, 34, 88, 1), 			new ColorPair(136, 254, 65, 55, 146, 1), 		new ColorPair(211, 255, 185, 85, 224, 1), 		new ColorPair(85, 224, 1, 55, 146, 1)),
	CERUCLASE("ceruclase", 			new ColorPair(69, 143, 171, 29, 60, 71), 		new ColorPair(140, 189, 208, 54, 112, 133), 	new ColorPair(191, 219, 230, 140, 189, 208), 	new ColorPair(140, 189, 208, 54, 112, 133)),
	ALDUORITE("alduorite", 			new ColorPair(163, 222, 222, 29, 78, 78), 		new ColorPair(213, 240, 240, 53, 145, 145), 	new ColorPair(241, 250, 250, 163, 222, 222), 	new ColorPair(163, 222, 222, 53, 145, 145)),
	INOLASHITE("inolashite",		new ColorPair(64, 170, 125, 30, 79, 57), 		new ColorPair(148, 216, 187, 44, 116, 85), 		new ColorPair(199, 235, 220, 64, 170, 125), 	new ColorPair(148, 216, 187, 30, 79, 57)),
	KALENDRITE("kalendrite",		new ColorPair(170, 91, 189, 98, 45, 111), 		new ColorPair(198, 145, 210, 122, 56, 139), 	new ColorPair(222, 193, 230, 170, 91, 189), 	new ColorPair(222, 193, 230, 122, 56, 139)),
	AMORDRINE("amordrine", 			new ColorPair(169, 141, 177, 113, 83, 121), 	new ColorPair(210, 196, 215, 143, 107, 154), 	new ColorPair(231, 223, 234, 169, 141, 177), 	new ColorPair(231, 223, 234, 143, 107, 154)),
	VULCANITE("vulcanite", 			new ColorPair(255, 132, 72, 204, 66, 0), 		new ColorPair(255, 176, 138, 242, 79, 0), 		new ColorPair(255, 209, 187, 255, 132, 72), 	new ColorPair(255, 209, 187, 255, 132, 72)),
	SANGUINITE("sanguinite", 		new ColorPair(185, 0, 0, 130, 0, 0), 			new ColorPair(255, 15, 15, 185, 0, 0), 			new ColorPair(255, 125, 125, 255, 15, 15), 		new ColorPair(255, 125, 125, 185, 0, 0)),
	PROMETHEUM("prometheum", 		new ColorPair(90, 129, 86, 41, 61, 39), 		new ColorPair(106, 152, 101, 53, 77, 51), 		new ColorPair(158, 189, 155, 90, 129, 86), 		new ColorPair(158, 189, 155, 90, 129, 86)),
	DEEP_IRON("deepIron", 			new ColorPair(73, 91, 105, 48, 61, 71), 		new ColorPair(121, 143, 162, 73, 91, 105), 		new ColorPair(167, 182, 194, 121, 143, 162), 	new ColorPair(167, 182, 194, 48, 61, 71)),
	INFUSCOLIUM("infuscolium", 		new ColorPair(146, 33, 86, 69, 16, 41), 		new ColorPair(221, 104, 159, 146, 33, 86), 		new ColorPair(242, 196, 218, 221, 104, 159), 	new ColorPair(221, 104, 159, 146, 33, 86)),
	BLACK_STEEL("blackSteel", 		new ColorPair(57, 86, 121, 28, 41, 57), 		new ColorPair(155, 180, 208, 57, 86, 121), 		new ColorPair(220, 230, 239, 155, 180, 208), 	new ColorPair(220, 230, 239, 57, 86, 121)),
	OURECLASE("oureclase", 			new ColorPair(183, 98, 21, 82, 44, 10), 		new ColorPair(231, 134, 46, 183, 98, 21), 		new ColorPair(243, 193, 148, 231, 134, 46), 	new ColorPair(2243, 193, 148, 183, 98, 21)),
	ASTRAL_SILVER("astralSilver", 	new ColorPair(184, 202, 203, 105, 141, 143), 	new ColorPair(209, 221, 222, 184, 202, 203), 	new ColorPair(235, 241, 241, 209, 221, 222), 	new ColorPair(235, 241, 241, 184, 202, 203)),
	CARMOT("carmot", 				new ColorPair(217, 205, 140, 99, 89, 33), 		new ColorPair(231, 224, 182, 133, 119, 44), 	new ColorPair(247, 244, 221, 177, 159, 58), 	new ColorPair(247, 244, 221, 133, 119, 44)),
	MITHRIL("mithril", 				new ColorPair(136, 240, 249, 5, 88, 95), 		new ColorPair(181, 245, 251, 9, 148, 159), 		new ColorPair(226, 252, 254, 11, 191, 206), 	new ColorPair(123, 224, 226, 9, 148, 159)),
	RUBRACIUM("rubracium", 			new ColorPair(151, 45, 45, 78, 22, 22), 		new ColorPair(208, 98, 98, 151, 45, 45), 		new ColorPair(230, 170, 170, 208, 98, 98), 		new ColorPair(230, 170, 170, 151, 45, 45)),
	QUICKSILVER("quicksilver", 		new ColorPair(124, 211, 199, 26, 77, 69), 		new ColorPair(209, 239, 235, 43, 128, 115), 	new ColorPair(244, 251, 250, 124, 211, 199), 	new ColorPair(209, 239, 235, 43, 128, 115)),
	HADEROTH("haderoth", 			new ColorPair(119, 52, 30, 44, 20, 12), 		new ColorPair(223, 89, 32, 119, 52, 30), 		new ColorPair(234, 145, 106, 223, 89, 32),		new ColorPair(234, 145, 106, 223, 89, 32)),
	ORICHALCUM("orichalcum", 		new ColorPair(84, 122, 56, 37, 54, 24), 		new ColorPair(162, 200, 134, 84, 122, 56), 		new ColorPair(232, 241, 224, 162, 200, 134),	new ColorPair(232, 241, 224, 84, 122, 56)),
	CELENEGIL("celenegil", 			new ColorPair(148, 204, 72, 54, 78, 22), 		new ColorPair(193, 225, 151, 100, 145, 40), 	new ColorPair(218, 237, 192, 148, 204, 72), 	new ColorPair(218, 237, 192, 100, 145, 40)),
	ADAMANTINE("adamantine",		new ColorPair(175, 1, 1, 101, 1, 1), 			new ColorPair(254, 67, 67, 175, 1, 1), 			new ColorPair(254, 160, 160, 254, 67, 67), 		new ColorPair(254, 67, 67, 175, 1, 1)),
	ATLARUS("atlarus", 				new ColorPair(204, 179, 0, 98, 86, 0), 			new ColorPair(255, 224, 4, 204, 179, 0), 		new ColorPair(255, 245, 174, 255, 224, 4), 		new ColorPair(255, 245, 174, 204, 179, 0)),
	TARTARITE("tartarite", 			new ColorPair(255, 118, 60, 121, 36, 0), 		new ColorPair(255, 182, 151, 174, 52, 0), 		new ColorPair(255, 226, 215, 255, 118, 60), 	new ColorPair(255, 182, 151, 174, 52, 0)),
	PHOSPHORITE("phosphorite", 		new ColorPair(162, 119, 119, 105, 71, 71),	 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(176, 140, 140, 120, 82, 82), 		new ColorPair(176, 140, 140, 105, 71, 71)),
	SULFUR("sulfur", 				new ColorPair(255, 242, 0, 252, 215, 3), 		new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(254, 243, 186, 255, 242, 0), 		new ColorPair(254, 243, 186, 252, 215, 3)),
	SALTPETER("saltpeter", 			new ColorPair(240, 240, 240, 225, 225, 225), 	new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(249, 249, 249, 240, 240, 240), 	new ColorPair(249, 249, 249, 240, 240, 240)),
	MAGNESIUM("magnesium", 			new ColorPair(147, 124, 108, 101, 84, 73), 		new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(179, 151, 132, 119, 100, 87), 	new ColorPair(147, 124, 108, 101, 84, 73)),
	BITUMEN("bitumen", 				new ColorPair(49, 49, 49, 22, 22, 22), 			new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(91, 91, 91, 36, 36, 36), 			new ColorPair(91, 91, 91, 49, 49, 49)),
	POTASH("potash", 				new ColorPair(239, 145, 3, 176, 88, 0), 		new ColorPair(255, 255, 255, 255, 255, 255), 	new ColorPair(253, 200, 121, 239, 145, 3), 		new ColorPair(253, 200, 121, 218, 132, 3)),
	EXIMITE("eximite", 				new ColorPair(101, 73, 122, 47, 33, 56), 		new ColorPair(124, 90, 150, 101, 73, 122), 		new ColorPair(158, 131, 180, 124, 90, 150), 	new ColorPair(158, 131, 180, 101, 73, 122)),
	MEUTOITE("meutoite", 			new ColorPair(70, 60, 78, 28, 24, 31), 			new ColorPair(95, 82, 105, 51, 44, 56), 		new ColorPair(152, 136, 164, 95, 82, 105), 		new ColorPair(152, 136, 164, 70, 60, 78)),
	DESICHALKOS("desichalkos", 		new ColorPair(114, 47, 168, 63, 26, 94), 		new ColorPair(158, 96, 210, 80, 33, 118), 		new ColorPair(187, 142, 223, 114, 47, 168), 	new ColorPair(187, 142, 223, 114, 47, 168));
	
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
