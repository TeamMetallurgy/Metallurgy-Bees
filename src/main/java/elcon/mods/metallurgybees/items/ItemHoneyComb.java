package elcon.mods.metallurgybees.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.metallurgybees.MetallurgyBees;
import elcon.mods.metallurgybees.types.MetallurgyBeeTypes;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemHoneyComb extends Item {
    IIcon icon1;
    IIcon icon2;

    public ItemHoneyComb() {
        super();
        setMaxDamage(0);
        setHasSubtypes(true);
        setCreativeTab(MetallurgyBees.creativeTab);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return StatCollector.translateToLocal("metallurgy.metals." + MetallurgyBeeTypes.values()[stack.getItemDamage()].name) + " " + StatCollector.translateToLocal(getUnlocalizedName());
    }

    @Override
    public String getUnlocalizedName() {
        return "item.metallurgyHoneyComb.name";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int meta, int renderPass) {
        if (renderPass > 0) {
            return this.icon1;
        }
        return this.icon2;

    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public int getRenderPasses(int meta) {
        return 2;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int renderPass) {
        if (renderPass > 0) {
            return MetallurgyBeeTypes.values()[stack.getItemDamage()].colorCombSecondary;
        }
        return MetallurgyBeeTypes.values()[stack.getItemDamage()].colorCombPrimary;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.icon1 = iconRegister.registerIcon("forestry:beeCombs.0");
        this.icon2 = iconRegister.registerIcon("forestry:beeCombs.1");

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
        for (int i = 0; i < MetallurgyBeeTypes.values().length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
