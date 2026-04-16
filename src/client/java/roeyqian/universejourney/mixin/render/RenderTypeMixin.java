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

// Mojang
import com.mojang.blaze3d.vertex.MeshData;

// Minecraft
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;

// JetBrains Specify
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Universe Journey
import roeyqian.universejourney.utility.mixin.renderer.RenderHelperForEquipment;

@Environment(EnvType.CLIENT)
@Mixin(value = RenderType.class, priority = 240000)
public abstract class RenderTypeMixin {

    @Shadow @Final private RenderSetup state;
    @Shadow @Final protected String name;

    @Inject(method = "draw", at = @At("HEAD"))
    private void beforeDraw(
            MeshData mesh,
            CallbackInfo ci
    ) {
        RenderHelperForEquipment.handleBeforeDraw(this, this.name, this.state);
    }

    @Inject(method = "draw", at = @At("TAIL"))
    private void afterDraw(
            MeshData mesh,
            CallbackInfo ci
    ) {
        RenderHelperForEquipment.handleAfterDraw(this, this.state);
    }

}