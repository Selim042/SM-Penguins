package selim.penguins.penguin;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import selim.penguins.Penguins;
import selim.penguins.layers.LayerApparelColored;
import selim.penguins.layers.LayerApparelPatterned;

public class PenguinRenderer extends RenderLiving<EntityPenguin> {

	public PenguinRenderer(RenderManager manager) {
		super(manager, new ModelPenguin(), 0.35f);
		this.layerRenderers.add(new LayerApparelColored(this));
		this.layerRenderers.add(new LayerApparelPatterned(this));
	}

	private static final ResourceLocation texture = new ResourceLocation(
			Penguins.MODID + ":textures/entities/penguin.png");

	@Override
	protected ResourceLocation getEntityTexture(EntityPenguin entity) {
		return texture;
	}

}
