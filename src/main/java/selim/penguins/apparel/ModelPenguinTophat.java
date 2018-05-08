package selim.penguins.apparel;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPenguinTophat extends ModelBase {

	private ModelRenderer brim;
	private ModelRenderer main;

	public ModelPenguinTophat() {
		this.textureWidth = 32;
		this.textureHeight = 32;
		this.brim = new ModelRenderer(this, 0, 0);
		// this.brim.setRotationPoint(-4.0F, 3.5F, -0.5F);
		this.brim.setRotationPoint(0.0f, 0.0f, 0.0f);
		this.brim.addBox(0.0F, 0.0F, -1.0F, 8, 1, 7, 0.0F);
		this.main = new ModelRenderer(this, 0, 8);
		// this.main.setRotationPoint(-3.0F, -1.5F, -0.5F);
		this.main.setRotationPoint(0.0f, 0.0f, 0.0f);
		this.main.addBox(0.0F, 0.0F, 0.0F, 6, 5, 5, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.brim.render(f5);
		this.main.render(f5);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
