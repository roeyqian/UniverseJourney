/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.mixin.entity;

// Minecraft
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluid;

// Sponge Powered Mixin
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Universe Journey
import roeyqian.universejourney.utility.mixin.entity.EntityHelperForEquipment;

@Mixin(value = Entity.class, priority = 240000)
public abstract class EntityMixin {

    @Shadow
    public abstract double getFluidHeight(TagKey<Fluid> fluid);

    @Inject(method = "tick", at = @At("HEAD"))
    private void inTick(
            CallbackInfo ci
    ) {
        if (!((Entity) (Object) this instanceof Player player)) {
            return;
        }
        EntityHelperForEquipment.handleUniverseBootsFluidWalking(player);
    }

    /* Universe Boots: Walking on water
    */
    @Inject(method = "isInWater", at = @At("HEAD"), cancellable = true)
    private void inIsTouchingWater(
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (!((Entity) (Object) this instanceof Player player)) {
            return;
        }
        EntityHelperForEquipment.handleUniverseBootsFluidContact(player, cir);
    }

    /* Universe Boots: Walking on lava
    */
    @Inject(method = "isInLava", at = @At("HEAD"), cancellable = true)
    private void inIsInLava(
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (!((Entity) (Object) this instanceof Player player)) {
            return;
        }
        EntityHelperForEquipment.handleUniverseBootsFluidContact(player, cir);
    }

}
