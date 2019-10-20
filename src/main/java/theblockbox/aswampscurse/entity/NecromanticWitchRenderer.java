package theblockbox.aswampscurse.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class NecromanticWitchRenderer extends MobRenderer<NecromanticWitchEntity, NecromanticWitchModel> {
    private static final ResourceLocation TEXTURES = new ResourceLocation("aswampscurse:textures/necromantic_witch.png");

    public NecromanticWitchRenderer(EntityRendererManager manager) {
        super(manager, new NecromanticWitchModel(), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(NecromanticWitchEntity necromanticWitchEntity) {
        return NecromanticWitchRenderer.TEXTURES;
    }
}
