package selim.penguins.apparel;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPenguinScarf extends ModelBase {

	public ModelRenderer neck;
	public ModelRenderer right;
	public ModelRenderer left;

	public ModelPenguinScarf() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.right = new ModelRenderer(this, 0, 0);
		this.right.setRotationPoint(1.0F, 8.0F, -3.5F);
		this.right.addBox(0.0F, 0.0F, 0.0F, 3, 7, 1, 0.0F);
		this.left = new ModelRenderer(this, 27, 0);
		this.left.setRotationPoint(-4.0F, 9.0F, -3.5F);
		this.left.addBox(0.0F, 0.0F, 0.0F, 3, 5, 1, 0.0F);
		this.neck = new ModelRenderer(this, 0, 0);
		this.neck.setRotationPoint(-4.5F, 7.5F, -2.5F);
		this.neck.addBox(0.0F, 0.0F, 0.0F, 9, 3, 9, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.right.render(f5);
		this.left.render(f5);
		this.neck.render(f5);
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
