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
import roeyqian.universejourney.entity.live.UniverseGuardian;
import roeyqian.universejourney.model.UniverseGuardianModel;
import roeyqian.universejourney.render.entity.state.UniverseGuardianRenderState;
import roeyqian.universejourney.utility.registry.output.RegEntityLayers;

@Environment(EnvType.CLIENT)
public class UniverseGuardianRenderer
        extends MobRenderer<UniverseGuardian, UniverseGuardianRenderState, UniverseGuardianModel> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(
            UniverseJourney.MOD_ID,
            "textures/entity/universe_guardian/base.png"
    );

    public UniverseGuardianRenderer(
            EntityRendererProvider.Context context
    ) {
        super(context, new UniverseGuardianModel(context.bakeLayer(RegEntityLayers.UNIVERSE_GUARDIAN)), 0.5F);
    }

    @Override
    public UniverseGuardianRenderState createRenderState() {
        return new UniverseGuardianRenderState();
    }

    @Override
    public void extractRenderState(
            UniverseGuardian entity,
            UniverseGuardianRenderState state,
            float partialTick
    ) {
        super.extractRenderState(entity, state, partialTick);
    }

    @Override @NonNull
    public Identifier getTextureLocation(
            UniverseGuardianRenderState state
    ) {
        return TEXTURE;
    }

}