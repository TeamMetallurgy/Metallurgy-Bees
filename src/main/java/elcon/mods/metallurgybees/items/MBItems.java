package elcon.mods.metallurgybees.items;

import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.metallurgybees.types.MetallurgyFrameTypes;
import net.minecraft.item.Item;

public class MBItems {

	public static Item honeyComb;
	public static Item beeGun;

	public static void initItems(){
		honeyComb = new ItemHoneyComb().setUnlocalizedName("metallurgyHoneyComb");
		for(MetallurgyFrameTypes frames :  MetallurgyFrameTypes.values()) {
			String itemSetName = frames.getName().substring(0, 1).toUpperCase() + frames.getName().substring(1).toLowerCase();
			Item hiveFrame = new ItemHiveFrame(frames).setUnlocalizedName("metallurgyFrame" + itemSetName);
			GameRegistry.registerItem(hiveFrame, "metallurgyFrame" + itemSetName);
		}
		beeGun = new ItemBeeGun().setUnlocalizedName("metallurgyBeegun");

		// register items
		GameRegistry.registerItem(honeyComb, "metallurgyHoneyComb");
	}
}
