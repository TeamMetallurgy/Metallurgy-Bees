package elcon.mods.metallurgybees;

import forestry.api.genetics.IAlleleFlowers;
import forestry.api.genetics.IFlowerProvider;

public class AlleleFlowers implements IAlleleFlowers {

	@Override
	public String getUID() {
		return "metallurgy.flower.stone";
	}

	@Override
	public boolean isDominant() {
		return true;
	}

	@Override
	public String getName() {
		return getProvider().getDescription();
	}

	@Override
	public IFlowerProvider getProvider() {
		return new FlowerProviderStone();
	}

}
