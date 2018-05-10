package selim.penguins.layers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.penguins.items.ItemApparelPatterned;
import selim.penguins.items.ItemApparelPatterned.ColoredPattern;
import selim.penguins.penguin.EntityPenguin;
import selim.penguins.penguin.ModelPenguin;

@SideOnly(Side.CLIENT)
public class LayerApparelPatterned implements LayerRenderer<EntityPenguin> {

	private final ModelBase model;
	private final RenderLiving<EntityPenguin> renderer;
	private final ResourceLocation texture;
	private final ItemApparelPatterned<?> apparel;

	public LayerApparelPatterned(RenderLiving<EntityPenguin> renderer, ModelBase model,
			ItemApparelPatterned<?> apparel, ResourceLocation textures) {
		this.renderer = renderer;
		this.model = model;
		this.texture = textures;
		this.apparel = apparel;
	}

	@Override
	public void doRenderLayer(EntityPenguin entity, float limbSwing, float limbSwingAmount,
			float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		ItemStack stack = entity.getSlot(this.apparel.getSlot());
		if (stack != null && stack.getItem().equals(this.apparel)) {
			ItemApparelPatterned<?> apparel = (ItemApparelPatterned<?>) stack.getItem();
			ColoredPattern<?>[] patterns = apparel.getPatterns(stack);
			GlStateManager.pushMatrix();
			// GlStateManager.depthFunc(GL11.GL_EQUAL);
			if (entity.isChild()) {
				if (apparel.shouldShinkIfBaby()) {
					GlStateManager.scale(0.5F, 0.5F, 0.5F);
					GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
				} else
					GlStateManager.translate(0.0F, ModelPenguin.childYOffset * scale,
							ModelPenguin.childZOffset * scale);
			}
			this.renderer.bindTexture(this.texture);
			this.model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,
					scale);
			for (ColoredPattern<?> pattern : patterns) {
				// System.out.println(pattern.getPattern() + ": " +
				// pattern.getColor());
				this.renderer.bindTexture(pattern.getPattern().getTexture());
				float[] colors = pattern.getColor().getColorComponentValues();
				// Color color = new Color(pattern.getColor().getColorValue());
				GlStateManager.color(colors[0], colors[1], colors[2]);
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
