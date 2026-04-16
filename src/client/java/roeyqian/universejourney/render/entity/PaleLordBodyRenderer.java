package roeyqian.universejourney.render.entity;

// Fabric
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

// Minecraft
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;

// JetBrains Specify
import org.jspecify.annotations.NonNull;

// Universe Journey
import roeyqian.universejourney.entity.live.PaleLordBody;
import roeyqian.universejourney.model.PaleLordModel;
import roeyqian.universejourney.utility.registry.output.RegEntityLayers;

@Environment(EnvType.CLIENT)
public class PaleLordBodyRenderer
        extends HumanoidMobRenderer<PaleLordBody, HumanoidRenderState, PaleLordModel> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(
            "minecraft",
            "textures/entity/creaking/creaking.png"
    );

    public PaleLordBodyRenderer(
            EntityRendererProvider.Context context
    ) {
        super(context, new PaleLordModel(context.bakeLayer(RegEntityLayers.PALE_LORD_BODY)), 0.5F);
    }

    @Override
    public HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }

    @Override @NonNull
    public Identifier getTextureLocation(
            HumanoidRenderState state
    ) {
        return TEXTURE;
    }

}

