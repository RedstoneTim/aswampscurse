package theblockbox.aswampscurse.entity;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;

/**
 * necromantic_witch - NoxEternisC
 * Created using Tabula 7.0.1
 */
public class NecromanticWitchModel extends BipedModel<NecromanticWitchEntity> {
    public RendererModel robe;
    public RendererModel nose;
    public RendererModel bhair;
    public RendererModel lhair;
    public RendererModel rhair;
    public RendererModel fhair;

    public NecromanticWitchModel() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.bipedLeftArm = new RendererModel(this, 40, 46);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.rhair = new RendererModel(this, 68, 0);
        this.rhair.mirror = true;
        this.rhair.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rhair.addBox(-3.9F, -8.5F, -3.5F, 8, 6, 10, 0.0F);
        NecromanticWitchModel.setRotateAngle(rhair, 1.0471975511965976F, -1.5707963267948966F, 0.0F);
        this.lhair = new RendererModel(this, 68, 0);
        this.lhair.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.lhair.addBox(-4.1F, -8.5F, -3.5F, 8, 6, 10, 0.0F);
        NecromanticWitchModel.setRotateAngle(lhair, 1.0471975511965976F, 1.5707963267948966F, 0.0F);
        this.bipedLeftLeg = new RendererModel(this, 0, 22);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.fhair = new RendererModel(this, 32, 0);
        this.fhair.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.fhair.addBox(-4.0F, -6.0F, -1.0F, 8, 6, 10, 0.0F);
        NecromanticWitchModel.setRotateAngle(fhair, 1.3962634015954636F, 0.0F, 0.0F);
        this.bipedBody = new RendererModel(this, 16, 20);
        this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedBody.addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, 0.0F);
        this.nose = new RendererModel(this, 24, 0);
        this.nose.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.nose.addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, 0.0F);
        this.bipedHead = new RendererModel(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHead.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
        this.robe = new RendererModel(this, 0, 38);
        this.robe.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.robe.addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, 0.5F);
        this.bipedRightArm = new RendererModel(this, 40, 46);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.bipedRightLeg = new RendererModel(this, 0, 22);
        this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.bhair = new RendererModel(this, 44, 16);
        this.bhair.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bhair.addBox(-4.0F, -8.5F, -3.5F, 8, 6, 10, 0.0F);
        NecromanticWitchModel.setRotateAngle(bhair, 1.0471975511965976F, 3.141592653589793F, 0.0F);
        this.bipedBody.addChild(this.bipedLeftArm);
        this.bipedHead.addChild(this.rhair);
        this.bipedHead.addChild(this.lhair);
        this.bipedBody.addChild(this.bipedLeftLeg);
        this.bipedHead.addChild(this.fhair);
        this.bipedHead.addChild(this.nose);
        this.bipedBody.addChild(this.bipedHead);
        this.bipedBody.addChild(this.bipedRightArm);
        this.bipedBody.addChild(this.bipedRightLeg);
        this.bipedHead.addChild(this.bhair);
    }

    @Override
    public void render(NecromanticWitchEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(entity, f, f1, f2, f3, f4, f5);
        this.bipedBody.render(f5);
        this.robe.render(f5);
    }

    @Override
    public void setRotationAngles(NecromanticWitchEntity entity, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
        this.leftArmPose = (entity.getGhoulSummoningState() == NecromanticWitchEntity.GhoulSummoningState.SUMMONING) ? ArmPose.THROW_SPEAR : ArmPose.EMPTY;
        this.rightArmPose = leftArmPose;
        super.setRotationAngles(entity, p_212844_2_, p_212844_3_, p_212844_4_, p_212844_5_, p_212844_6_, p_212844_7_);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    private static void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
