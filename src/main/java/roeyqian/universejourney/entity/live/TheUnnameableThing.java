/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.entity.live;

// Minecraft
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

// JetBrains Specify
import org.jspecify.annotations.NonNull;

public class TheUnnameableThing extends Mob {

    private static final float REFLECT_RATIO = 0.5F;

    public TheUnnameableThing(
            EntityType<? extends Mob> entityType,
            Level world
    ) {
        super(entityType, world);
        this.setNoAi(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1000000000.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0F)
                .add(Attributes.ARMOR, 12.0F)
                .add(Attributes.ARMOR_TOUGHNESS, 8.0F);
    }

    @Override
    protected void registerGoals() {}

    @Override
    public boolean hurtServer(
            @NonNull ServerLevel world,
            DamageSource source,
            float amount
    ) {
        if (source.getEntity() instanceof LivingEntity attacker
                && attacker.isAlive()
                && attacker != this
                && !source.is(DamageTypes.THORNS)
        ) {
            float reflectDamage = Math.max(0.0F, amount * REFLECT_RATIO);
            if (reflectDamage > 0.0F) {
                attacker.hurtServer(world, this.damageSources().thorns(this), reflectDamage);
            }
        }

        return super.hurtServer(world, source, amount);
    }

}
