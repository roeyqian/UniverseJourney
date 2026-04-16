/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.render.entity;

// Fabric
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

// Minecraft
import net.minecraft.client.model.animal.golem.IronGolemModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.IronGolemCrackinessLayer;
import net.minecraft.client.renderer.entity.layers.IronGolemFlowerLayer;
import net.minecraft.client.renderer.entity.state.IronGolemRenderState;
import net.minecraft.resources.Identifier;

// JetBrains Specify
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.NonNull;

// Universe Journey
import roeyqian.universejourney.entity.live.ObsidianGolem;
import roeyqian.universejourney.model.ObsidianGolemModel;
import roeyqian.universejourney.render.entity.state.ObsidianGolemRenderState;
import roeyqian.universejourney.utility.registry.output.RegEntityLayers;

@Environment(EnvType.CLIENT)
public class ObsidianGolemRenderer
        extends MobRenderer<ObsidianGolem, ObsidianGolemRenderState, ObsidianGolemModel> {

    public static final BlockDisplayContext BLOCK_DISPLAY_CONTEXT = BlockDisplayContext.create();
    private static final Identifier GOLEM_LOCATION = Identifier.withDefaultNamespace("textures/entity/iron_golem/iron_golem.png");
    private final BlockModelResolver blockModelResolver;

    public ObsidianGolemRenderer(final EntityRendererProvider.Context context) {
        super(context, new ObsidianGolemModel(context.bakeLayer(ModelLayers.IRON_GOLEM)), 0.7F);
        this.blockModelResolver = context.getBlockModelResolver();
    }

    public Identifier getTextureLocation(final ObsidianGolemRenderState state) {
        return GOLEM_LOCATION;
    }

    public ObsidianGolemRenderState createRenderState() {
        return new ObsidianGolemRenderState();
    }

    public void extractRenderState(final ObsidianGolem entity, final ObsidianGolemRenderState state, final float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.attackTicksRemaining = (float)entity.getAttackAnimationTick() > 0.0F ? (float)entity.getAttackAnimationTick() - partialTicks : 0.0F;
        state.offerFlowerTick = entity.getOfferFlowerTick();
        if (state.offerFlowerTick > 0) {
            this.blockModelResolver.update(state.flowerBlock, Blocks.POPPY.defaultBlockState(), BLOCK_DISPLAY_CONTEXT);
        } else {
            state.flowerBlock.clear();
        }

        state.crackiness = entity.getCrackiness();
    }

    protected void setupRotations(final ObsidianGolemRenderState state, final PoseStack poseStack, final float bodyRot, final float entityScale) {
        super.setupRotations(state, poseStack, bodyRot, entityScale);
        if (!((double)state.walkAnimationSpeed < 0.01)) {
            float p = 13.0F;
            float wp = state.walkAnimationPos + 6.0F;
            float triangleWave = (Math.abs(wp % 13.0F - 6.5F) - 3.25F) / 3.25F;
            poseStack.mulPose(Axis.ZP.rotationDegrees(6.5F * triangleWave));
        }
    }

}

