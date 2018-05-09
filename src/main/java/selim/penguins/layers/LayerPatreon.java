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

	private float hue = 0;
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
		PatronType patreonType = PatreonHelper.getPatronType(entity.getUniqueID());
		GlStateManager.pushMatrix();
		GlStateManager.rotate(netHeadYaw, 0, 1, 0);
		GlStateManager.rotate(headPitch, 1, 0, 0);
		translateToHeadLevel(entity);
		GlStateManager.translate(0.0f, 0.75f, -0.2f);
		GlStateManager.scale(1.5f, 1.5f, 1.5f);
		for (int i = 0; i < textures.length; i++) {
			// ResourceLocation tex = this.textures[i];
			// this.renderer.bindTexture(tex);
			this.renderer.bindTexture(textures[i]);
			if (i == 1) {
				int rgb = patreonType.getColor();
				Color color;
				if (rgb == -2)
					color = Color.getHSBColor(hue += 0.003f, 1.0f, 1.0f);
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

	private static void translateToHeadLevel(EntityPlayer player) {
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
