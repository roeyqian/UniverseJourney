/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.entity.live;

// Minecraft
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;

// JetBrains Specify
import org.jspecify.annotations.NonNull;

// Java Standard
import java.util.EnumSet;

public class BellSoul extends Monster {

    private static final double MAX_HEALTH = 14.0;
    private static final double ATTACK_DAMAGE = 4.0;
    private static final double CHASE_SPEED_MODIFIER = 2.0;
    private static final double ROAM_SPEED_MODIFIER = 1.5;

    public BellSoul(
            EntityType<? extends Monster> entityType,
            Level world
    ) {
        super(entityType, world);
        this.noPhysics = true;
        this.moveControl = new BellSoulMoveControl();
        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.setPathfindingMalus(PathType.LAVA, 0.0F);
        this.xpReward = 5;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, MAX_HEALTH)
                .add(Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE)
                .add(Attributes.MOVEMENT_SPEED, 2.0)
                .add(Attributes.FLYING_SPEED, 2.0)
                .add(Attributes.FOLLOW_RANGE, 48.0);
    }

    @Override
    protected @NonNull PathNavigation createNavigation(
            @NonNull Level world
    ) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, world);
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(true);
        return navigation;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SimpleMeleeAttackGoal());
        this.goalSelector.addGoal(8, new RandomMoveGoal());
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(
                2,
                new NearestAttackableTargetGoal<>(
                        this,
                        LivingEntity.class,
                        10,
                        true,
                        false,
                        (entity, _) -> !(entity instanceof Player)
                                && !(entity instanceof BellRinger)
                                && !(entity instanceof BellSoul)
                )
        );
    }

    @Override
    public void aiStep() {
        this.setNoGravity(true);
        this.noPhysics = true;
        this.fallDistance = 0.0F;
        super.aiStep();
    }

    @Override
    protected void doPush(
            @NonNull Entity entity
    ) {}

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canAttack(
            @NonNull LivingEntity target
    ) {
        if (target instanceof BellRinger || target instanceof BellSoul) return false;
        return super.canAttack(target);
    }

    @Override
    protected @NonNull SoundEvent getAmbientSound() {
        return SoundEvents.VEX_AMBIENT;
    }

    @Override
    protected @NonNull SoundEvent getHurtSound(
            @NonNull DamageSource source
    ) {
        return SoundEvents.VEX_HURT;
    }

    @Override
    protected @NonNull SoundEvent getDeathSound() {
        return SoundEvents.VEX_DEATH;
    }

    public boolean isChargingAttack() {
        return this.swinging;
    }

    class SimpleMeleeAttackGoal extends Goal {

        private static final int ATTACK_INTERVAL_TICKS = 20;
        private static final double ATTACK_RANGE_SQR = 2.25;
        private int attackCooldown = 0;

        public SimpleMeleeAttackGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = BellSoul.this.getTarget();
            return target != null && target.isAlive() && BellSoul.this.canAttack(target);
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = BellSoul.this.getTarget();
            return target != null && target.isAlive() && BellSoul.this.canAttack(target);
        }

        @Override
        public void tick() {
            LivingEntity target = BellSoul.this.getTarget();
            if (target == null) return;

            BellSoul.this.getLookControl().setLookAt(target, 30.0F, 30.0F);
            BellSoul.this.getMoveControl().setWantedPosition(
                    target.getX(),
                    target.getY(0.5),
                    target.getZ(),
                    CHASE_SPEED_MODIFIER
            );

            if (attackCooldown > 0) attackCooldown--;

            if (BellSoul.this.distanceToSqr(target) <= ATTACK_RANGE_SQR && attackCooldown <= 0) {
                attackCooldown = ATTACK_INTERVAL_TICKS;
                BellSoul.this.swing(InteractionHand.MAIN_HAND);
                if (BellSoul.this.level() instanceof ServerLevel serverWorld) {
                    target.hurtServer(
                            serverWorld,
                            BellSoul.this.damageSources().mobAttack(BellSoul.this),
                            (float) BellSoul.this.getAttributeValue(Attributes.ATTACK_DAMAGE)
                    );
                }
            }
        }
    }

    class BellSoulMoveControl extends MoveControl {

        private static final double ACCELERATION = 0.12;

        public BellSoulMoveControl() {
            super(BellSoul.this);
        }

        @Override
        public void tick() {
            if (this.operation != Operation.MOVE_TO) return;

            Vec3 toTarget = new Vec3(
                    this.wantedX - BellSoul.this.getX(),
                    this.wantedY - BellSoul.this.getY(),
                    this.wantedZ - BellSoul.this.getZ()
            );
            double distance = toTarget.length();
            if (distance < 1.0E-5) {
                this.operation = Operation.WAIT;
                return;
            }

            Vec3 acceleration = toTarget.scale((this.speedModifier * ACCELERATION) / distance);
            BellSoul.this.setDeltaMovement(BellSoul.this.getDeltaMovement().add(acceleration));

            Vec3 velocity = BellSoul.this.getDeltaMovement();
            if (BellSoul.this.getTarget() == null) {
                BellSoul.this.setYRot(-((float) Mth.atan2(velocity.x, velocity.z)) * (180.0F / (float) Math.PI));
            }
            else {
                double dx = BellSoul.this.getTarget().getX() - BellSoul.this.getX();
                double dz = BellSoul.this.getTarget().getZ() - BellSoul.this.getZ();
                BellSoul.this.setYRot(-((float) Mth.atan2(dx, dz)) * (180.0F / (float) Math.PI));
            }
            BellSoul.this.yBodyRot = BellSoul.this.getYRot();
        }
    }

    class RandomMoveGoal extends Goal {

        public RandomMoveGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return BellSoul.this.getTarget() == null
                    && !BellSoul.this.getMoveControl().hasWanted()
                    && BellSoul.this.random.nextInt(reducedTickDelay(7)) == 0;
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void start() {
            Vec3 origin = BellSoul.this.position();
            for (int i = 0; i < 3; i++) {
                double x = origin.x + (BellSoul.this.random.nextDouble() * 2.0 - 1.0) * 12.0;
                double y = origin.y + (BellSoul.this.random.nextDouble() * 2.0 - 1.0) * 8.0;
                double z = origin.z + (BellSoul.this.random.nextDouble() * 2.0 - 1.0) * 12.0;

                BlockPos targetPos = BlockPos.containing(x, y, z);
                if (!BellSoul.this.level().isEmptyBlock(targetPos)) continue;

                BellSoul.this.getMoveControl().setWantedPosition(x, y, z, ROAM_SPEED_MODIFIER);
                if (BellSoul.this.getTarget() == null) {
                    BellSoul.this.getLookControl().setLookAt(x, y, z, 180.0F, 20.0F);
                }
                break;
            }
        }
    }
}
