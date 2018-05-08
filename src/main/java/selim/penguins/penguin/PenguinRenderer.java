package selim.penguins.penguin;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import selim.penguins.Penguins;
import selim.penguins.apparel.ModelPenguinBowtie;
import selim.penguins.apparel.ModelPenguinTophat;
import selim.penguins.layers.LayerApparelColored;

public class PenguinRenderer extends RenderLiving<EntityPenguin> {

	public PenguinRenderer(RenderManager manager) {
		super(manager, new ModelPenguin(), 0.35f);
		this.layerRenderers
				.add(new LayerApparelColored(this, new ModelPenguinBowtie(), Penguins.Items.BOWTIE,
						new ResourceLocation(Penguins.MODID, "textures/apparel/bowtie.png")));
		this.layerRenderers
				.add(new LayerApparelColored(this, new ModelPenguinTophat(), Penguins.Items.TOPHAT,
						new ResourceLocation(Penguins.MODID, "textures/apparel/tophat.png"),
						new ResourceLocation(Penguins.MODID, "textures/apparel/tophat_stripe.png")));
		// this.layerRenderers
		// .add(new LayerApparelPatterned(this, new ModelPenguinScarf(),
		// Penguins.Items.SCARF,
		// new ResourceLocation(Penguins.MODID, "textures/apparel/scarf.png")));
		// this.layerRenderers.add(new LayerApparel(this, new ModelPenguinFez(),
		// Penguins.Items.FEZ,
		// new ResourceLocation(Penguins.MODID, "textures/apparel/fez.png")));
	}

	private static final ResourceLocation texture = new ResourceLocation(
			Penguins.MODID + ":textures/entities/penguin.png");

	@Override
	protected ResourceLocation getEntityTexture(EntityPenguin entity) {
		return texture;
	}

}
