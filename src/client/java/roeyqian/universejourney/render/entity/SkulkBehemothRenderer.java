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
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

// JetBrains Specify
import org.jspecify.annotations.NonNull;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;
import roeyqian.universejourney.entity.live.SkulkBehemoth;
import roeyqian.universejourney.model.SkulkBehemothModel;
import roeyqian.universejourney.render.entity.state.SkulkBehemothRenderState;
import roeyqian.universejourney.utility.registry.output.RegEntityLayers;

@Environment(EnvType.CLIENT)
public class SkulkBehemothRenderer
        extends MobRenderer<SkulkBehemoth, SkulkBehemothRenderState, SkulkBehemothModel> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(
            UniverseJourney.MOD_ID,
            "textures/entity/skulk_behemoth/base.png"
    );

    public SkulkBehemothRenderer(
            EntityRendererProvider.Context context
    ) {
        super(context, new SkulkBehemothModel(context.bakeLayer(RegEntityLayers.SKULK_BEHEMOTH)), 2.0F);
    }

    @Override @NonNull
    public Identifier getTextureLocation(
            SkulkBehemothRenderState state
    ) {
        return TEXTURE;
    }

    @Override
    public SkulkBehemothRenderState createRenderState() {
        return new SkulkBehemothRenderState();
    }

    @Override
    public void extractRenderState(
            SkulkBehemoth entity,
            SkulkBehemothRenderState state,
            float partialTick
    ) {
        super.extractRenderState(entity, state, partialTick);
        state.phaseType = entity.getPhaseType();
        state.inAir = !entity.onGround();
    }

}