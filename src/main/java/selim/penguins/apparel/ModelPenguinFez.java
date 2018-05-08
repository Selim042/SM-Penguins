package selim.penguins.apparel;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelPenguin - Either Mojang or a mod author
 * Created using Tabula 7.0.0
 */
public class ModelPenguinFez extends ModelBase {
    public ModelRenderer shape8;
    public ModelRenderer shape9;

    public ModelPenguinFez() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.shape9 = new ModelRenderer(this, 0, 12);
        this.shape9.setRotationPoint(1.0F, -0.5F, 1.5F);
        this.shape9.addBox(0.0F, 0.0F, 0.0F, 2, 4, 1, 0.0F);
        this.shape8 = new ModelRenderer(this, 0, 0);
        this.shape8.setRotationPoint(-2.5F, 0.0F, -0.5F);
        this.shape8.addBox(0.0F, 0.0F, 0.0F, 5, 4, 5, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.shape9.render(f5);
        this.shape8.render(f5);
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
