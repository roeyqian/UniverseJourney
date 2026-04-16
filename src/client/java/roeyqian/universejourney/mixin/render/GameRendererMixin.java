/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.mixin.render;

// Minecraft
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;

// SpongePowered Mixin
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Universe Journey
import roeyqian.universejourney.utility.mixin.renderer.RenderHelperForFunction;

@Mixin(value = GameRenderer.class, priority = 240000)
public class GameRendererMixin {

    @Shadow @Final
    private Minecraft minecraft;
    @Shadow
    private float spinningEffectSpeed;

    /* Custom Portal: Vanilla Portal Nausea Effect Features */
    @Inject(method = "tick", at = @At(value = "FIELD",
            target = "Lnet/minecraft/client/renderer/GameRenderer;spinningEffectSpeed:F",
            opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER))
    private void boostUniversePortalSpeed(
            CallbackInfo ci
    ) {
        this.spinningEffectSpeed = RenderHelperForFunction.handleTick(this.minecraft, this.spinningEffectSpeed);
    }

}