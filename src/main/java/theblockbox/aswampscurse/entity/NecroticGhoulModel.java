package theblockbox.aswampscurse.entity;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;

/**
 * NecroticGhoulModel - NoxEternisC
 * Created using Tabula 7.0.1
 */
public class NecroticGhoulModel extends BipedModel<NecroticGhoulEntity> {
    public RendererModel spines;
    public RendererModel jaw;
    public RendererModel rjawhinge;
    public RendererModel ljawhinge;

    public NecroticGhoulModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.rjawhinge = new RendererModel(this, 32, 5);
        this.rjawhinge.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rjawhinge.addBox(-3.8F, -3.0F, -5.0F, 0, 5, 5, 0.0F);
        this.ljawhinge = new RendererModel(this, 32, 5);
        this.ljawhinge.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.ljawhinge.addBox(3.8F, -3.0F, -5.0F, 0, 5, 5, 0.0F);
        this.bipedRightLeg = new RendererModel(this, 0, 16);
        this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        NecroticGhoulModel.setRotateAngle(bipedRightLeg, -0.4363323129985824F, 0.0F, 0.0F);
        this.bipedLeftArm = new RendererModel(this, 40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.setRotationPoint(4.0F, 1.0F, 0.0F);
        this.bipedLeftArm.addBox(0.0F, -1.0F, -2.0F, 3, 16, 4, 0.0F);
        NecroticGhoulModel.setRotateAngle(bipedLeftArm, -0.4363323129985824F, 0.0F, 0.0F);
        this.jaw = new RendererModel(this, 32, 0);
        this.jaw.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.jaw.addBox(-4.0F, 0.0F, -8.0F, 8, 2, 8, 0.0F);
        NecroticGhoulModel.setRotateAngle(jaw, 0.045553093477052F, 0.0F, 0.0F);
        this.bipedHead = new RendererModel(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, -1.9F);
        this.bipedHead.addBox(-4.0F, -6.0F, -8.0F, 8, 6, 8, 0.0F);
        NecroticGhoulModel.setRotateAngle(bipedHead, -0.40980330836826856F, 0.0F, 0.0F);
        this.bipedLeftLeg = new RendererModel(this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        NecroticGhoulModel.setRotateAngle(bipedLeftLeg, -0.4363323129985824F, 0.0F, 0.0F);
        this.bipedBody = new RendererModel(this, 16, 16);
        this.bipedBody.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
        NecroticGhoulModel.setRotateAngle(bipedBody, 0.4363323129985824F, 0.0F, 0.0F);
        this.bipedRightArm = new RendererModel(this, 40, 16);
        this.bipedRightArm.setRotationPoint(-4.0F, 1.0F, 0.0F);
        this.bipedRightArm.addBox(-3.0F, -1.0F, -2.0F, 3, 16, 4, 0.0F);
        NecroticGhoulModel.setRotateAngle(bipedRightArm, -0.4363323129985824F, 0.0F, 0.0F);
        this.spines = new RendererModel(this, 0, 24);
        this.spines.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.spines.addBox(0.0F, -2.5F, 0.0F, 0, 14, 10, 0.0F);
        this.jaw.addChild(this.rjawhinge);
        this.jaw.addChild(this.ljawhinge);
        this.bipedBody.addChild(this.bipedRightLeg);
        this.bipedBody.addChild(this.bipedLeftArm);
        this.bipedHead.addChild(this.jaw);
        this.bipedBody.addChild(this.bipedHead);
        this.bipedBody.addChild(this.bipedLeftLeg);
        this.bipedBody.addChild(this.bipedRightArm);
        this.bipedBody.addChild(this.spines);
    }

    @Override
    public void render(NecroticGhoulEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.bipedBody.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public static void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
