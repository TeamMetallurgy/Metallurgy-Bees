package elcon.mods.metallurgybees;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
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

public class AlleleBeeSpecies implements IAlleleBeeSpecies, IIconProvider {

	IIcon[][] icons;
	private HashMap<ItemStack, Integer> products = new HashMap<ItemStack, Integer>();

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
		return "metallurgy.bees." + this.name;
	}

	@Override
	public IBeeRoot getRoot() {
		return MetallurgyBees.beeRoot;
	}

	@Override
	public String getDescription() {
		return "test";
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
	public float getResearchSuitability(ItemStack itemstack) {
		return 0;
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

	public AlleleBeeSpecies addProduct(ItemStack product, int chance) {
		this.products.put(product, Integer.valueOf(chance));
		return this;
	}

	@Override
	public Map<ItemStack, Integer> getProducts() {
		return this.products;
	}

	@Override
	public Map<ItemStack, Integer> getSpecialty() {
		return new HashMap<ItemStack, Integer>();
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

}