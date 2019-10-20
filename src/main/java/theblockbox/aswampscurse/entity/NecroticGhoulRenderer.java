package theblockbox.aswampscurse.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class NecroticGhoulRenderer extends MobRenderer<NecroticGhoulEntity, NecroticGhoulModel> {
    private static final ResourceLocation TEXTURES = new ResourceLocation("aswampscurse:textures/necrotic_ghoul.png");

    public NecroticGhoulRenderer(EntityRendererManager manager) {
        super(manager, new NecroticGhoulModel(), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(NecroticGhoulEntity necroticGhoulEntity) {
        return NecroticGhoulRenderer.TEXTURES;
    }
}
