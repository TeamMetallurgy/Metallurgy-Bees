package elcon.mods.metallurgybees.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.core.config.ForestryItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.ItemPotion;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderBee extends Render {

	public void doRender(Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2) {
		IIcon localIcon = ForestryItem.beeDroneGE.item().getIconFromDamage(0);
		if(localIcon == null) {
			return;
		}

		GL11.glPushMatrix();

		GL11.glTranslatef((float) paramDouble1, (float) paramDouble2, (float) paramDouble3);
		GL11.glEnable(32826);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		bindEntityTexture(paramEntity);

		Tessellator localTessellator = Tessellator.instance;

		if(localIcon == ItemPotion.func_94589_d("bottle_splash")) {
			int i = PotionHelper.func_77915_a(((EntityPotion) paramEntity).getPotionDamage(), false);
			float f1 = (i >> 16 & 0xFF) / 255.0F;
			float f2 = (i >> 8 & 0xFF) / 255.0F;
			float f3 = (i & 0xFF) / 255.0F;

			GL11.glColor3f(f1, f2, f3);
			GL11.glPushMatrix();
			func_77026_a(localTessellator, ItemPotion.func_94589_d("overlay"));
			GL11.glPopMatrix();
			GL11.glColor3f(1.0F, 1.0F, 1.0F);
		}

		func_77026_a(localTessellator, localIcon);

		GL11.glDisable(32826);
		GL11.glPopMatrix();
	}

	protected ResourceLocation getEntityTexture(Entity paramEntity) {
		return TextureMap.locationItemsTexture;
	}

	private void func_77026_a(Tessellator p_77026_1_, IIcon p_77026_2_) {
		float f1 = p_77026_2_.getMinU();
		float f2 = p_77026_2_.getMaxU();
		float f3 = p_77026_2_.getMinV();
		float f4 = p_77026_2_.getMaxV();
		float f5 = 1.0F;
		float f6 = 0.5F;
		float f7 = 0.25F;
		GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		p_77026_1_.startDrawingQuads();
		p_77026_1_.setNormal(0.0F, 1.0F, 0.0F);
		p_77026_1_.addVertexWithUV(0.0F - f6, 0.0F - f7, 0.0D, f1, f4);
		p_77026_1_.addVertexWithUV(f5 - f6, 0.0F - f7, 0.0D, f2, f4);
		p_77026_1_.addVertexWithUV(f5 - f6, f5 - f7, 0.0D, f2, f3);
		p_77026_1_.addVertexWithUV(0.0F - f6, f5 - f7, 0.0D, f1, f3);
		p_77026_1_.draw();
	}
}