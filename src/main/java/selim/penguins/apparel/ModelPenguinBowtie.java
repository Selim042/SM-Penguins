package selim.penguins.apparel;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPenguinBowtie extends ModelBase {

	public ModelRenderer center;
	public ModelRenderer right;
	public ModelRenderer left;

	public ModelPenguinBowtie() {
		this.textureWidth = 16;
		this.textureHeight = 16;
		this.center = new ModelRenderer(this, 6, 0);
		this.center.setRotationPoint(-0.5F, 10.5F, -2.6F);
		this.center.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.right = new ModelRenderer(this, 0, 0);
		this.right.setRotationPoint(0.5F, 10.1F, -2.7F);
		this.right.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
		this.left = new ModelRenderer(this, 10, 0);
		this.left.setRotationPoint(-2.5F, 10.1F, -2.7F);
		this.left.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.center.render(f5);
		this.right.render(f5);
		this.left.render(f5);
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
