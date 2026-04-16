/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.mixin.render;

// Fabric
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

// Minecraft
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.feature.ItemFeatureRenderer;

// JetBrains Specify
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Universe Journey
import roeyqian.universejourney.utility.mixin.renderer.RenderHelperForEquipment;

@Environment(EnvType.CLIENT)
@Mixin(value = ItemFeatureRenderer.class, priority = 240000)
public abstract class ItemFeatureRendererMixin {

    @Inject(method = "renderItem(Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;" +
            "Lnet/minecraft/client/renderer/OutlineBufferSource;" +
            "Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;)V",
            at = @At("HEAD"))
    private void universe$beforeRenderItem(
            MultiBufferSource.BufferSource bufferSource,
            OutlineBufferSource outlineBufferSource,
            SubmitNodeStorage.ItemSubmit submit,
            CallbackInfo ci
    ) {
        RenderHelperForEquipment.handleRenderItemHead(submit);
    }

}