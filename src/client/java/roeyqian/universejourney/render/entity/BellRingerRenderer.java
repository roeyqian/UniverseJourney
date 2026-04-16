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
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;

// JetBrains Specify
import org.jspecify.annotations.NonNull;

// Universe Journey
import roeyqian.universejourney.entity.live.BellRinger;
import roeyqian.universejourney.model.BellRingerModel;
import roeyqian.universejourney.utility.registry.output.RegEntityLayers;

@Environment(EnvType.CLIENT)
public class BellRingerRenderer
        extends HumanoidMobRenderer<BellRinger, HumanoidRenderState, BellRingerModel> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(
            "minecraft",
            "textures/entity/zombie/zombie.png"
    );

    public BellRingerRenderer(
            EntityRendererProvider.Context context
    ) {
        super(context, new BellRingerModel(context.bakeLayer(RegEntityLayers.BELL_RINGER)), 0.5F);
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

