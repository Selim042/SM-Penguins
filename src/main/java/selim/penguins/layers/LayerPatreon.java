package selim.penguins.layers;

import java.awt.Color;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.penguins.PatreonHelper;
import selim.penguins.PatreonHelper.PatronType;

@SideOnly(Side.CLIENT)
public class LayerPatreon implements LayerRenderer<EntityPlayer> {

	private static float hue = 0;

	private final ModelBase model;
	private final RenderPlayer renderer;
	private final ResourceLocation[] textures;

	public LayerPatreon(RenderPlayer renderer, ModelBase model, ResourceLocation... textures) {
		this.renderer = renderer;
		this.model = model;
		this.textures = textures;
	}

	@Override
	public void doRenderLayer(EntityPlayer entity, float limbSwing, float limbSwingAmount,
			float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (!PatreonHelper.isPatron(entity.getUniqueID()))
			return;
		// this.model.setModelAttributes(this.renderer.getMainModel());
		// this.model.setLivingAnimations(entity, limbSwing, limbSwingAmount,
		// partialTicks);
		PatronType patreonType = PatreonHelper.getPatronType(entity.getUniqueID());
		GlStateManager.pushMatrix();
		translateToHeadLevel(entity);
		// GlStateManager.translate(0.0f, 4.0f, 0.0f);
		// if (entity.isSneaking())
		// GlStateManager.translate(0.0f, -0.38f, -0.15f);
		// else
		// GlStateManager.translate(0.0f, -0.56f, -0.15f);
		GlStateManager.rotate(netHeadYaw, 0, 1, 0);
		GlStateManager.rotate(headPitch, 1, 0, 0);
		GlStateManager.translate(0.0f, 2.0f, 0.0f);
		// // GlStateManager.scale(1.5f, 1.5f, 1.5f);
		for (int i = 0; i < textures.length; i++) {
			// ResourceLocation tex = this.textures[i];
			// this.renderer.bindTexture(tex);
			this.renderer.bindTexture(textures[i]);
			if (i == 1) {
				int rgb = patreonType.getColor();
				Color color;
				if (rgb == -2)
					color = Color.getHSBColor(hue += 1.0f, 1.0f, 1.0f);
				else
					color = new Color(rgb);
				GlStateManager.color(color.getRed() / 255f, color.getGreen() / 255f,
						color.getBlue() / 255f);
			}
			this.model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,
					scale);
		}
		GlStateManager.popMatrix();
	}

	public static void translateToHeadLevel(EntityPlayer player) {
		GlStateManager.translate(0, -player.getDefaultEyeHeight(), 0);
		if (player.isSneaking())
			GlStateManager.translate(
					0.25F * MathHelper.sin(player.rotationPitch * (float) Math.PI / 180),
					0.25F * MathHelper.cos(player.rotationPitch * (float) Math.PI / 180), 0F);
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

}
