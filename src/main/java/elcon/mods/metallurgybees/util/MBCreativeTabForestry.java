package elcon.mods.metallurgybees.util;

import elcon.mods.metallurgybees.MetallurgyBees;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class MBCreativeTabForestry extends CreativeTabs {

    public MBCreativeTabForestry(String label) {
        super(label);
    }

    @Override
    public Item getTabIconItem() {
        return MetallurgyBees.honeyComb;
    }

    @Override
    public String getTranslatedTabLabel() {
        return "itemGroup.MetallurgyAddonForestry";
    }
}
