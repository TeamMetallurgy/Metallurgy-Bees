package elcon.mods.metallurgybees;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
