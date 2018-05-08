package selim.penguins.layers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.penguins.items.ItemApparel;
import selim.penguins.penguin.EntityPenguin;
import selim.penguins.penguin.ModelPenguin;

@SideOnly(Side.CLIENT)
public class LayerApparel implements LayerRenderer<EntityPenguin> {

	private final ModelBase model;
	private final RenderLiving<EntityPenguin> renderer;
	private final ResourceLocation texture;
	private final ItemApparel apparel;

	public LayerApparel(RenderLiving<EntityPenguin> renderer, ModelBase model, ItemApparel apparel,
			ResourceLocation texture) {
		this.renderer = renderer;
		this.model = model;
		this.texture = texture;
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
			this.renderer.bindTexture(texture);
			this.model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,
					scale);
			GlStateManager.popMatrix();
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

}
