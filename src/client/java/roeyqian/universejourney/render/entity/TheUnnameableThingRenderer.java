/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.render.entity;

// Fabric
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

// Minecraft
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

// JetBrains Specify
import org.jspecify.annotations.NonNull;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;
import roeyqian.universejourney.entity.live.TheUnnameableThing;
import roeyqian.universejourney.model.TheUnnameableThingModel;
import roeyqian.universejourney.render.entity.state.TheUnnameableThingRenderState;
import roeyqian.universejourney.utility.registry.output.RegEntityLayers;

@Environment(EnvType.CLIENT)
public class TheUnnameableThingRenderer
        extends MobRenderer<TheUnnameableThing, TheUnnameableThingRenderState, TheUnnameableThingModel> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(
            UniverseJourney.MOD_ID,
            "textures/entity/the_unnameable_thing/base.png"
    );
    private static final float MODEL_SCALE = 5.0F;

    public TheUnnameableThingRenderer(
            EntityRendererProvider.Context context
    ) {
        super(context, new TheUnnameableThingModel(context.bakeLayer(RegEntityLayers.THE_UNNAMEABLE_THING)), 0.35F * MODEL_SCALE);
    }

    @Override
    public TheUnnameableThingRenderState createRenderState() {
        return new TheUnnameableThingRenderState();
    }

    @Override
    protected void scale(
            TheUnnameableThingRenderState state,
            PoseStack poseStack
    ) {
        super.scale(state, poseStack);
        poseStack.scale(MODEL_SCALE, MODEL_SCALE, MODEL_SCALE);
    }

    @Override @NonNull
    public Identifier getTextureLocation(
            TheUnnameableThingRenderState state
    ) {
        return TEXTURE;
    }

}
