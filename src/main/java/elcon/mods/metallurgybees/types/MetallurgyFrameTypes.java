package elcon.mods.metallurgybees.types;

import elcon.mods.metallurgybees.items.ItemHiveFrame;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeModifier;

public enum MetallurgyFrameTypes implements IBeeModifier {

	REINFORCED(MetallurgyBeeTypes.SHADOW_IRON.name, 540, 1f, 1f, 2.5f, 2f, 0.8f),
	FORTIFIED(MetallurgyBeeTypes.SHADOW_STEEL.name, 1080, 1f, 1f, 2.5f, 1.5f, 0.8f),
	MIDAS(MetallurgyBeeTypes.MIDASIUM.name, 360, 1f, 1f, 2.5f, 1f, 0.8f),
	MUTATING(MetallurgyBeeTypes.VYROXERES.name, 360, 1f, 2.0f, 1.5f, 0.0f, 0.8f),
	FERTILE(MetallurgyBeeTypes.INOLASHITE.name, 480, 1f, 1f, 0.5f, 2f, 0.8f),
	ANCIENT(MetallurgyBeeTypes.KALENDRITE.name, 360, 1f, 1f, 1.5f, 2.0f, 0.8f),
	IMMORTAL(MetallurgyBeeTypes.AMORDRINE.name, 360, 1f, 1f, 2.0f, 2.0f, 0.8f),
	MOLTEN(MetallurgyBeeTypes.VULCANITE.name, 360, 1f, 1f, 0.5f, 3.0f, 0.8f),
	SANGUINE(MetallurgyBeeTypes.SANGUINITE.name, 360, 1f, 0.0f, 3.0f, 2.0f, 0.8f);

	public int maxDamage;
	public String frameName;
	
	private final float territoryMod;
	private final float mutationMod;
	private final float lifespanMod;
	private final float productionMod;
	private final float floweringMod;
	private final float geneticDecayMod;
	private final boolean isSealed;
	private final boolean isLit;
	private final boolean isSunlit;
	private final boolean isHellish;

	public float productionModifer = 1;
	public float floweringModifer = 1;
	public float lifespanModifer = 1;
	public float mutationModifier = 1;
	public float territoryModifer = 1;

	MetallurgyFrameTypes(String name, int damage, float territory, float mutation, float lifespan, float production, float geneticDecay) {
		this(name, damage, territory, mutation, lifespan, production, 1f, geneticDecay, false, false, false, false);
	}
	
	MetallurgyFrameTypes(String name, int maxDamage, float territory, float mutation, float lifespan, float production, float flowering,
			float geneticDecay, boolean sealed, boolean lit, boolean sunlit, boolean hellish) {
		this.frameName = name;
		this.maxDamage = maxDamage;
		
		this.territoryMod = territory;
		this.mutationMod = mutation;
		this.lifespanMod = lifespan;
		this.productionMod = production;
		this.floweringMod = flowering;
		this.geneticDecayMod = geneticDecay;
		this.isSealed = sealed;
		this.isLit = lit;
		this.isSunlit = sunlit;
		this.isHellish = hellish;
	}
	
	public String getName() {
		return this.frameName;
	}

	@Override
	public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
		return territoryMod;
	}

	@Override
	public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
		return mutationMod;
	}

	@Override
	public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
		return lifespanMod;
	}

	@Override
	public float getProductionModifier(IBeeGenome genome, float currentModifier) {
		return productionMod;
	}

	@Override
	public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
		return floweringMod;
	}

	@Override
	public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
		return geneticDecayMod;
	}

	@Override
	public boolean isSealed() {
		return isSealed;
	}

	@Override
	public boolean isSelfLighted() {
		return isLit;
	}

	@Override
	public boolean isSunlightSimulated() {
		return isSunlit;
	}

	@Override
	public boolean isHellish() {
		return isHellish;
	}
}
