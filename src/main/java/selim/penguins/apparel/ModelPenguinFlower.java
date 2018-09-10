package selim.penguins.apparel;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPenguinFlower extends ModelBase {

	public ModelRenderer shape8;
	public ModelRenderer shape8_1;
	public ModelRenderer shape8_2;
	public ModelRenderer shape8_3;
	public ModelRenderer shape8_4;

	public ModelPenguinFlower() {
		this.textureWidth = 16;
		this.textureHeight = 16;
		this.shape8 = new ModelRenderer(this, 4, 2);
		this.shape8.setRotationPoint(-3.0F, 4.5F, -1.5F);
		this.shape8.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.shape8_3 = new ModelRenderer(this, 4, 4);
		this.shape8_3.setRotationPoint(-3.0F, 5.5F, -1.5F);
		this.shape8_3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.shape8_4 = new ModelRenderer(this, 4, 0);
		this.shape8_4.setRotationPoint(-3.0F, 3.5F, -1.5F);
		this.shape8_4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.shape8_1 = new ModelRenderer(this, 0, 2);
		this.shape8_1.setRotationPoint(-4.0F, 4.5F, -1.5F);
		this.shape8_1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.shape8_2 = new ModelRenderer(this, 8, 2);
		this.shape8_2.setRotationPoint(-2.0F, 4.5F, -1.5F);
		this.shape8_2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.shape8.render(f5);
		this.shape8_3.render(f5);
		this.shape8_4.render(f5);
		this.shape8_1.render(f5);
		this.shape8_2.render(f5);
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
