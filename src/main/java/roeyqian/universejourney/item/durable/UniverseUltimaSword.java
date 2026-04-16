/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.item.durable;

// Minecraft
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

// JetBrains Specify
import org.jspecify.annotations.NonNull;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;
import roeyqian.universejourney.entity.dead.UniverseFireball;
import roeyqian.universejourney.item.CustomItemSettings;
import roeyqian.universejourney.utility.registry.gen.RegComponentTypes;

public class UniverseUltimaSword extends Item {

    public UniverseUltimaSword(
            Properties settings
    ) {
        super(applySettings(settings));
    }

    @Override
    public void hurtEnemy(
            @NonNull ItemStack stack,
            @NonNull LivingEntity target,
            @NonNull LivingEntity user
    ) {
        if (!(user instanceof Player player)) return;
        if (!(player.level() instanceof ServerLevel world)) return;

        target.igniteForTicks(100);
        int mode = stack.getOrDefault(RegComponentTypes.UNIVERSE_ULTIMA_SWORD_MODE, 0);
        if (mode == 0) {
            target.hurtServer(world, world.damageSources().playerAttack(player), Integer.MAX_VALUE);
        } else {
            execUniverseKill(world, target);
            if (target.isAlive()) target.discard();
        }
    }

    @Override @NonNull
    public InteractionResult use(
            @NonNull Level world,
            Player player,
            @NonNull InteractionHand hand
    ) {
        int mode = player.getItemInHand(hand).getOrDefault(RegComponentTypes.UNIVERSE_ULTIMA_SWORD_MODE, 0);
        return mode == 1 ? execFireballMode(world, player) : InteractionResult.PASS;
    }

    @Override @NonNull
    public InteractionResult useOn(
            UseOnContext context
    ) {
        int mode = context.getItemInHand().getOrDefault(RegComponentTypes.UNIVERSE_ULTIMA_SWORD_MODE, 1);
        return mode == 0 ? execFlintMode(context) : InteractionResult.PASS;
    }

    private static Properties applySettings(
            Properties settings
    ) {
        return CustomItemSettings.applyUniverseDefaults(settings)
                .component(
                        DataComponents.LORE,
                        CustomItemSettings.universeLore("universe_ultima_sword", 3)
                )
                .component(
                        DataComponents.TOOL,
                        CustomItemSettings.createTool(0.1F, 1, false)
                )
                .component(
                        DataComponents.ATTRIBUTE_MODIFIERS,
                        createAttributes()
                );
    }

    private static ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.BLOCK_INTERACTION_RANGE,
                        new AttributeModifier(
                                Identifier.fromNamespaceAndPath(UniverseJourney.MOD_ID, "universe_block_range"),
                                1024.0F,
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ENTITY_INTERACTION_RANGE,
                        new AttributeModifier(
                                Identifier.fromNamespaceAndPath(UniverseJourney.MOD_ID, "universe_entity_range"),
                                1024.0F,
                                AttributeModifier.Operation.ADD_VALUE
                        ), EquipmentSlotGroup.MAINHAND
                )
                .build();
    }

    private static void execUniverseKill(
            ServerLevel world,
            LivingEntity target
    ) {
        EntityType.LIGHTNING_BOLT.spawn(world, target.blockPosition(), EntitySpawnReason.TRIGGERED);
        world.playSound(
                null,
                target.getX(), target.getY(), target.getZ(),
                SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.WEATHER,
                100.0F, 1.0F
        );
        target.kill(world);
    }

    private static InteractionResult execFireballMode(
            Level world, Player player
    ) {
        if (!(world instanceof ServerLevel)) return InteractionResult.PASS;

        double speedMultiplier = 5.0F;
        Vec3 lookVec = player.getViewVector(1.0F);
        Vec3 velocity = lookVec.scale(speedMultiplier);
        UniverseFireball fireball = new UniverseFireball(world, player, velocity, 15);

        fireball.setDeltaMovement(velocity);
        fireball.setPos(
                player.getX() + lookVec.x * 1.5,
                player.getY() + player.getEyeHeight() + lookVec.y * 0.5,
                player.getZ() + lookVec.z * 1.5
        );

        world.addFreshEntity(fireball);
        world.playSound(
                null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.GHAST_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F
        );

        return InteractionResult.SUCCESS;
    }

    private static InteractionResult execFlintMode(
            UseOnContext context
    ) {
        Level world = context.getLevel();
        Player player = context.getPlayer();
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = world.getBlockState(blockPos);

        if (!CampfireBlock.canLight(blockState)
                && !CandleBlock.canLight(blockState)
                && !CandleCakeBlock.canLight(blockState)
        ) {
            BlockPos blockPos2 = blockPos.relative(context.getClickedFace());

            if (BaseFireBlock.canBePlacedAt(world, blockPos2, context.getHorizontalDirection())) {
                world.playSound(
                        null,
                        blockPos2,
                        SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS,
                        1.0F, 1.0F
                );

                BlockState blockState2 = BaseFireBlock.getState(world, blockPos2);
                world.setBlock(blockPos2, blockState2, Block.UPDATE_ALL_IMMEDIATE);
                world.gameEvent(player, GameEvent.BLOCK_PLACE, blockPos);
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.FAIL;
            }
        } else {
            world.playSound(
                    player, blockPos,
                    SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS,
                    1.0F, 1.0F
            );

            world.setBlock(
                    blockPos, blockState.setValue(BlockStateProperties.LIT, true),
                    Block.UPDATE_ALL_IMMEDIATE
            );
            world.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
            return InteractionResult.SUCCESS;
        }
    }

}
