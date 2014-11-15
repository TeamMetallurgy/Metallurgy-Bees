package elcon.mods.metallurgybees.types;

public enum MetallurgyFrameTypes {

    REINFORCED(MetallurgyBeeTypes.SHADOW_IRON, 540),
    FORTIFIED(MetallurgyBeeTypes.SHADOW_STEEL, 1080),
    MIDAS(MetallurgyBeeTypes.MIDASIUM, 360),
    MUTATING(MetallurgyBeeTypes.VYROXERES, 360),
    FERTILE(MetallurgyBeeTypes.INOLASHITE, 480),
    ANCIENT(MetallurgyBeeTypes.KALENDRITE, 360),
    IMMORTAL(MetallurgyBeeTypes.AMORDRINE, 360),
    MOLTEN(MetallurgyBeeTypes.VULCANITE, 360),
    SANGUINE(MetallurgyBeeTypes.SANGUINITE, 360);

    public int maxDamage;
    public MetallurgyBeeTypes type;

    public float productionModifer = 1;
    public float floweringModifer = 1;
    public float lifespanModifer = 1;
    public float mutationModifier = 1;
    public float territoryModifer = 1;

    MetallurgyFrameTypes(MetallurgyBeeTypes type, int maxDamage) {
        this.type = type;
        this.maxDamage = maxDamage;
    }

    public static void init() {
        REINFORCED.setProductionModifier(2F);
        FORTIFIED.setProductionModifier(1.5F);

        MUTATING.setProductionModifier(0.0F);
        MUTATING.setLifespanModifier(1.5F);
        MUTATING.setMutationModifier(2.0F);

        FERTILE.setProductionModifier(2f);
        FERTILE.setLifespanModifier(0.5f);

        ANCIENT.setProductionModifier(2.0F);
        ANCIENT.setLifespanModifier(1.5F);

        IMMORTAL.setProductionModifier(2.0F);
        IMMORTAL.setLifespanModifier(2.0F);

        MOLTEN.setProductionModifier(3.0F);
        MOLTEN.setLifespanModifier(0.5F);

        SANGUINE.setProductionModifier(2.0F);
        SANGUINE.setLifespanModifier(3.0F);
        SANGUINE.setMutationModifier(0.0F);
    }

    public MetallurgyFrameTypes setFloweringModifier(float modifer) {
        this.floweringModifer = modifer;
        return this;
    }

    public MetallurgyFrameTypes setLifespanModifier(float modifer) {
        this.lifespanModifer = modifer;
        return this;
    }

    public MetallurgyFrameTypes setMutationModifier(float modifer) {
        this.mutationModifier = modifer;
        return this;
    }

    public MetallurgyFrameTypes setProductionModifier(float modifer) {
        this.productionModifer = modifer;
        return this;
    }

    public MetallurgyFrameTypes setTerritoryModifier(float modifer) {
        this.territoryModifer = modifer;
        return this;
    }
}
