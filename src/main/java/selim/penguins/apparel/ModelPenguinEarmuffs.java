package selim.penguins.apparel;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPenguinEarmuffs extends ModelBase {
    public ModelRenderer topBrace;
    public ModelRenderer rightBrace;
    public ModelRenderer rightMuff;
    public ModelRenderer leftBrace;
    public ModelRenderer leftMuff;

    public ModelPenguinEarmuffs() {
        this.textureWidth = 32;
        this.textureHeight = 16;
        this.rightMuff = new ModelRenderer(this, 21, 0);
        this.rightMuff.setRotationPoint(3.5F, 5.0F, 0.5F);
        this.rightMuff.addBox(0.0F, 0.0F, 0.0F, 1, 3, 3, 0.0F);
        this.rightBrace = new ModelRenderer(this, 0, 3);
        this.rightBrace.setRotationPoint(-4.5F, 4.0F, 1.0F);
        this.rightBrace.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
        this.leftMuff = new ModelRenderer(this, 3, 3);
        this.leftMuff.setRotationPoint(-4.5F, 5.0F, 0.5F);
        this.leftMuff.addBox(0.0F, 0.0F, 0.0F, 1, 3, 3, 0.0F);
        this.leftBrace = new ModelRenderer(this, 18, 0);
        this.leftBrace.setRotationPoint(3.5F, 4.0F, 1.0F);
        this.leftBrace.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
        this.topBrace = new ModelRenderer(this, 0, 0);
        this.topBrace.setRotationPoint(-3.5F, 3.0F, 1.0F);
        this.topBrace.addBox(0.0F, 0.0F, 0.0F, 7, 1, 2, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.rightMuff.render(f5);
        this.rightBrace.render(f5);
        this.leftMuff.render(f5);
        this.leftBrace.render(f5);
        this.topBrace.render(f5);
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
