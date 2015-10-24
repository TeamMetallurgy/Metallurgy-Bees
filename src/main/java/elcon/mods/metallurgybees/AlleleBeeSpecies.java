package elcon.mods.metallurgybees;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.mojang.authlib.GameProfile;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.IIconProvider;
import forestry.api.genetics.IClassification;
import forestry.api.genetics.IIndividual;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class AlleleBeeSpecies implements IAlleleBeeSpecies, IIconProvider {

	IIcon[][] icons;
	private HashMap<ItemStack, Float> products;
	private HashMap<ItemStack, Float> specialties;

	String uid;
	boolean dominant;
	String name;
	IClassification branch;
	String binomial;
	int colorBeeRoughPrimary;
	int colorBeeRoughSecondary;
	EnumTemperature temperature = EnumTemperature.NORMAL;

	public AlleleBeeSpecies(String uid, boolean dominant, String name, IClassification branch, String binomial, int colorBeeRoughPrimary, int colorBeeRoughSecondary) {
		this.uid = uid;
		this.dominant = dominant;
		this.name = name;
		this.branch = branch;
		this.binomial = binomial;
		this.colorBeeRoughPrimary = colorBeeRoughPrimary;
		this.colorBeeRoughSecondary = colorBeeRoughSecondary;
		this.products = new HashMap<ItemStack, Float>();
		this.specialties = new HashMap<ItemStack, Float>();
	}

	@Override
	public String getUID() {
		return this.uid;
	}

	@Override
	public String getName() {		
		return StatCollector.translateToLocal(this.getUnlocalizedName());
	}
	
	@Override
	public String getUnlocalizedName() {
		return this.name;
	}

	@Override
	public IBeeRoot getRoot() {
		return MetallurgyBees.beeRoot;
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public String getBinomial() {
		return binomial;
	}

	@Override
	public String getAuthority() {
		return "Shadowclaimer";
	}

	@Override
	public IClassification getBranch() {
		return this.branch;
	}

	@Override
	public int getComplexity() {
		return 0;
	}

	@Override
	public float getResearchSuitability(ItemStack itemStack) {
		if (itemStack == null) {
			return 0f;
		}

		for (ItemStack product : this.products.keySet()) {
			if (itemStack.isItemEqual(product)) {
				return 1f;
			}
		}

		for (ItemStack specialty : this.specialties.keySet()) {
			if (specialty.isItemEqual(itemStack)) {
				return 1f;
			}
		}

		/*if (itemStack.getItem() == ForestryHelper.honeyDrop) {
			return 0.5f;
		} else if (itemStack.getItem() == ForestryHelper.honeydew) {
			return 0.7f;
		} else if (itemStack.getItem() == ForestryHelper.beeComb || itemStack.getItem() == Config.combs) {
			return 0.4f;
		} else*/ if (getRoot().isMember(itemStack)) {
			return 1.0f;
		} else {
			for (Map.Entry<ItemStack, Float> catalyst : BeeManager.beeRoot.getResearchCatalysts().entrySet()) {
				if (OreDictionary.itemMatches(itemStack, catalyst.getKey(), false)) {
					return catalyst.getValue();
				}
			}
		}

		return 0f;
	}

	@Override
	public ItemStack[] getResearchBounty(World world, GameProfile gameProfile, IIndividual individual, int bountyLevel) {
		return new ItemStack[0];
	}

	@Override
	public boolean hasEffect() {
		return false;
	}

	@Override
	public boolean isSecret() {
		return false;
	}

	@Override
	public boolean isCounted() {
		return true;
	}

	@Override
	public boolean isDominant() {
		return dominant;
	}

	@Override
	public boolean isNocturnal() {
		return false;
	}

	public void setTemperature(EnumTemperature temp) {
		this.temperature = temp;
	}

	@Override
	public EnumTemperature getTemperature() {
		return temperature;
	}

	@Override
	public EnumHumidity getHumidity() {
		return EnumHumidity.NORMAL;
	}

	@Override
	public int getIconColour(int render) {
		if(render == 0)
			return this.colorBeeRoughPrimary;
		if(render == 1)
			return this.colorBeeRoughSecondary;
		return 16777215;
	}

	@Override
	public IIconProvider getIconProvider() {
		return this;
	}

	public AlleleBeeSpecies addProduct(ItemStack product, float chance) {
		products.put(product, chance);
		return this;
	}
	
	public AlleleBeeSpecies addSpecialty(ItemStack produce, float chance) {
		specialties.put(produce, chance);
		return this;
	}

	@Override
	public boolean isJubilant(IBeeGenome genome, IBeeHousing housing) {
		return false;
	}

	@Override
	public IIcon getIcon(short texUID) {
		return null;
	}

	@Override
	public void registerIcons(IIconRegister register) {
		String iconType = "default";
		String mod = "forestry";

		this.icons = new IIcon[EnumBeeType.values().length][3];

		IIcon body1 = register.registerIcon(mod + ":bees/" + iconType + "/body1");

		for(int i = 0; i < EnumBeeType.values().length; i++)
			if(EnumBeeType.values()[i] != EnumBeeType.NONE) {
				this.icons[i][0] = register.registerIcon(mod + ":bees/" + iconType + "/" + EnumBeeType.values()[i].toString().toLowerCase(Locale.ENGLISH) + ".outline");
				this.icons[i][1] = (EnumBeeType.values()[i] != EnumBeeType.LARVAE ? body1 : register.registerIcon(mod + ":bees/" + iconType + "/" + EnumBeeType.values()[i].toString().toLowerCase(Locale.ENGLISH) + ".body"));

				this.icons[i][2] = register.registerIcon(mod + ":bees/" + iconType + "/" + EnumBeeType.values()[i].toString().toLowerCase(Locale.ENGLISH) + ".body2");
			}
	}

	@Override
	public IIcon getIcon(EnumBeeType type, int renderPass) {
		return this.icons[type.ordinal()][renderPass];
	}

	@Override
	public String getEntityTexture() {
		return "textures/entity/bees/honeyBee.png";
	}

	@Override
	public Map<ItemStack, Float> getProductChances() {
		return products;
	}

	@Override
	public Map<ItemStack, Float> getSpecialtyChances() {
		return specialties;
	}

}