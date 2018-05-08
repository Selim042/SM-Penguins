package selim.penguins.layers;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.penguins.items.ItemApparelColored;
import selim.penguins.items.ItemBowtie;
import selim.penguins.penguin.EntityPenguin;
import selim.penguins.penguin.ModelPenguin;

@SideOnly(Side.CLIENT)
public class LayerApparelColored implements LayerRenderer<EntityPenguin> {

	private static final ItemColors COLORS;

	static {
		COLORS = Minecraft.getMinecraft().getItemColors();
	}

	private final ModelBase model;
	private final RenderLiving<EntityPenguin> renderer;
	private final ResourceLocation[] textures;
	private final ItemApparelColored apparel;

	public LayerApparelColored(RenderLiving<EntityPenguin> renderer, ModelBase model,
			ItemApparelColored apparel, ResourceLocation... textures) {
		this.renderer = renderer;
		this.model = model;
		this.textures = textures;
		this.apparel = apparel;
	}

	@Override
	public void doRenderLayer(EntityPenguin entity, float limbSwing, float limbSwingAmount,
			float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		ItemStack stack = entity.getSlot(this.apparel.getSlot());
		if (stack != null && stack.getItem().equals(this.apparel)) {
			GlStateManager.pushMatrix();
			if (entity.isChild()) {
				if (apparel.shouldShinkIfBaby()) {
					GlStateManager.scale(0.5F, 0.5F, 0.5F);
					GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
				} else
					GlStateManager.translate(0.0F, ModelPenguin.childYOffset * scale,
							ModelPenguin.childZOffset * scale);
			}
			for (int i = 0; i < textures.length; i++) {
				// ResourceLocation tex = this.textures[i];
				// this.renderer.bindTexture(tex);
				this.renderer.bindTexture(textures[i]);
				int rgb = -2;
				rgb = COLORS.colorMultiplier(stack, i);
				if (rgb == -2)
					rgb = ItemBowtie.getColor(stack);
				Color color = new Color(rgb);
				GlStateManager.color(color.getRed() / 255f, color.getGreen() / 255f,
						color.getBlue() / 255f);
				this.model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,
						scale);
			}
			GlStateManager.popMatrix();
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

}
