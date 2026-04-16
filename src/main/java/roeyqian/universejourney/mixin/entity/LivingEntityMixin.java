/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.mixin.entity;

// Minecraft
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;

// Sponge Powered Mixin
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Universe Journey
import roeyqian.universejourney.utility.mixin.entity.EntityHelperForEquipment;
import roeyqian.universejourney.utility.registry.item.RegDurableItems;

@Mixin(value = LivingEntity.class, priority = 240000)
public abstract class LivingEntityMixin {

    @Shadow
    protected abstract SoundEvent getDeathSound();

    @Unique
    private int flightTicks = 0;

    /* Universe Ultima Sword: Absolute Strike
    */
    @Inject(method = "hurtServer", at = @At("RETURN"))
    private void inHurtServerHead(
            ServerLevel world,
            DamageSource source,
            float amount,
            CallbackInfoReturnable<Boolean> cir
    ) {
        EntityHelperForEquipment.handleUniverseUltimaSwordHitEffect(
                (LivingEntity) (Object) this,
                this.getDeathSound(),
                cir.getReturnValue(),
                source
        );
    }

    /* Universe Helmet: Immunity to Negative Visual & Food Effects
    */
    @Inject(method = "forceAddEffect", at = @At("HEAD"), cancellable = true)
    private void inStatusEffect(
            MobEffectInstance effect,
            Entity source,
            CallbackInfo ci
    ) {
        if (!((LivingEntity) (Object) this instanceof Player player)) {
            return;
        }
        EntityHelperForEquipment.handleUniverseHelmetImmunity(player, effect, ci);
    }

    /* Universe Chestplate & Universe Leggings: Fast-Flying
    */
    @Inject(method = "baseTick", at = @At("TAIL"))
    private void inBaseTick(
            CallbackInfo ci
    ) {
        int result;
        if (!((LivingEntity) (Object) this instanceof Player player)) {
            result = this.flightTicks;
        } else {
            float defaultSpeed = new Abilities().getFlyingSpeed();
            boolean isDefaultSpeed = player.getAbilities().getFlyingSpeed() != defaultSpeed;
            boolean allowFlying = player.getItemBySlot(EquipmentSlot.CHEST).is(RegDurableItems.UNIVERSE_CHESTPLATE);
            if (allowFlying) {
                result = EntityHelperForEquipment.handleUniverseFlight(player, this.flightTicks);
            } else {
                if (isDefaultSpeed) {
                    EntityHelperForEquipment.handleDefaultFlight(player, defaultSpeed);
                }
                result = this.flightTicks;
            }
        }

        this.flightTicks = result;
    }

    /* Universe Leggings: Smooth Movement
    */
    @Inject(method = "tick", at = @At("HEAD"))
    private void inTick(
            CallbackInfo ci
    ) {
        EntityHelperForEquipment.handleUniverseLeggingsStepping((LivingEntity) (Object) this);
    }


}
