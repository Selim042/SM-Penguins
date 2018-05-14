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
import selim.penguins.ApparelModelManager;
import selim.penguins.Penguins;
import selim.penguins.items.ItemApparel.EnumPenguinSlot;
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

	private final RenderLiving<EntityPenguin> renderer;

	public LayerApparelColored(RenderLiving<EntityPenguin> renderer) {
		this.renderer = renderer;
	}

	@Override
	public void doRenderLayer(EntityPenguin entity, float limbSwing, float limbSwingAmount,
			float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		for (EnumPenguinSlot slot : EnumPenguinSlot.values()) {
			ItemStack stack = entity.getSlot(slot);
			if (stack != null && stack.getItem() instanceof ItemApparelColored) {
				ItemApparelColored apparel = (ItemApparelColored) stack.getItem();
				GlStateManager.pushMatrix();
				if (entity.isChild()) {
					if (apparel.shouldShinkIfBaby()) {
						GlStateManager.scale(0.5F, 0.5F, 0.5F);
						GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
					} else
						GlStateManager.translate(0.0F, ModelPenguin.childYOffset * scale,
								ModelPenguin.childZOffset * scale);
				}
				ModelBase model = ApparelModelManager.getModel(apparel);
				ResourceLocation[] textures = ApparelModelManager.getTextures(apparel);
				if (model == null || textures == null || textures.length == 0) {
					GlStateManager.popMatrix();
					Penguins.LOGGER.error("Model or texture for " + apparel + "("
							+ apparel.getRegistryName() + ") not found.");
					continue;
				}
				for (int i = 0; i < textures.length; i++) {
					this.renderer.bindTexture(textures[i]);
					int rgb = -2;
					rgb = COLORS.colorMultiplier(stack, i);
					if (rgb == -2)
						rgb = ItemBowtie.getColor(stack);
					Color color = new Color(rgb);
					GlStateManager.color(color.getRed() / 255f, color.getGreen() / 255f,
							color.getBlue() / 255f);
					model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,
							scale);
				}
				GlStateManager.popMatrix();
			}
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

}
