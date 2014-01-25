package elcon.mods.metallurgybees;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialBeeHive extends Material {

	public MaterialBeeHive() {
		super(MapColor.stoneColor);
		setRequiresTool();
		setImmovableMobility();
	}
}
