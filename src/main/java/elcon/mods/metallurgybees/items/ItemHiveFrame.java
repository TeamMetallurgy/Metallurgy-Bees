package elcon.mods.metallurgybees.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.metallurgybees.MetallurgyBees;
import elcon.mods.metallurgybees.types.MetallurgyFrameTypes;
import elcon.mods.metallurgybees.util.MBUtil;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IHiveFrame;
import net.minecraft.util.StatCollector;

public class ItemHiveFrame extends Item implements IHiveFrame {

    public MetallurgyFrameTypes types;

    public ItemHiveFrame(MetallurgyFrameTypes types) {
        super();
        this.types = types;
        setMaxStackSize(1);
        setMaxDamage(0);
        setCreativeTab(MetallurgyBees.creativeTab);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return StatCollector.translateToLocal("metallurgy.frames." + types.name().toLowerCase()) + " " + StatCollector.translateToLocal(getUnlocalizedName());
    }

    @Override
    public String getUnlocalizedName() {
        return "item.metallurgyFrame.name";
    }

    @Override
    public float getFloweringModifier(IBeeGenome beeGenome, float currentModifier) {
        return types.floweringModifer;
    }

    @Override
    public float getGeneticDecay(IBeeGenome beeGenome, float currentModifier) {
        return 1.0F;
    }

    @Override
    public float getLifespanModifier(IBeeGenome beeGenome1, IBeeGenome beeGenome2, float currentModifier) {
        return types.lifespanModifer;
    }

    @Override
    public float getMutationModifier(IBeeGenome beeGenome1, IBeeGenome beeGenome2, float currentModifier) {
        return types.mutationModifier;
    }

    @Override
    public float getProductionModifier(IBeeGenome beeGenome, float currentModifier) {
        return currentModifier < 16.0F ? types.productionModifer : 1.0F;
    }

    @Override
    public float getTerritoryModifier(IBeeGenome beeGenome, float currentModifier) {
        return types.territoryModifer;
    }

    @Override
    public boolean isHellish() {
        return false;
    }

    @Override
    public boolean isSealed() {
        return false;
    }

    @Override
    public boolean isSelfLighted() {
        return false;
    }

    @Override
    public boolean isSunlightSimulated() {
        return false;
    }

    @Override
    public ItemStack frameUsed(IBeeHousing housing, ItemStack frame, IBee queen, int wear) {
        if(getMaxDamage() == 0) {
            setMaxDamage(types.maxDamage);
        }
        frame.setItemDamage(frame.getItemDamage() + wear);
        if(frame.getItemDamage() >= frame.getMaxDamage()) {
            return null;
        }
        return frame;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("metallurgybees:frame" + MBUtil.firstUpperCase(types.name().toLowerCase()));
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        par3List.add("Mutation Modifier: " + types.mutationModifier);
        par3List.add("Flowering Modifier: " + types.floweringModifer);
        par3List.add("Production Modifier: " + types.productionModifer);
        par3List.add("Territory Modifier: " + types.territoryModifer);
        par3List.add("LifeSpan Modifier: " + types.lifespanModifer);
    }
}