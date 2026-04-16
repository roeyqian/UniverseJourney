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
import roeyqian.universejourney.entity.live.BellSoul;
import roeyqian.universejourney.model.BellSoulModel;
import roeyqian.universejourney.render.entity.state.BellSoulRenderState;
import roeyqian.universejourney.utility.registry.output.RegEntityLayers;

@Environment(EnvType.CLIENT)
public class BellSoulRenderer
        extends MobRenderer<BellSoul, BellSoulRenderState, BellSoulModel> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(
            "minecraft",
            "textures/entity/illager/vex.png"
    );

    public BellSoulRenderer(
            EntityRendererProvider.Context context
    ) {
        super(context, new BellSoulModel(context.bakeLayer(RegEntityLayers.BELL_SOUL)), 0.3F);
    }

    @Override
    public BellSoulRenderState createRenderState() {
        return new BellSoulRenderState();
    }

    @Override
    public void extractRenderState(
            BellSoul entity,
            BellSoulRenderState state,
            float partialTick
    ) {
        super.extractRenderState(entity, state, partialTick);
        state.charging = entity.isChargingAttack();
    }

    @Override
    protected void scale(
            BellSoulRenderState state,
            PoseStack poseStack
    ) {
        super.scale(state, poseStack);
        // Align the visual model center with BellSoul's small hitbox.
        poseStack.translate(0.0F, -0.35F, 0.0F);
    }

    @Override @NonNull
    public Identifier getTextureLocation(
            BellSoulRenderState state
    ) {
        return TEXTURE;
    }
}
