/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.utility.mixin.server;

// Minecraft
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

// Sponge Powered Mixin
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Universe Journey
import roeyqian.universejourney.utility.registry.gen.RegComponentTypes;
import roeyqian.universejourney.utility.registry.item.RegDurableItems;

public final class ServerHelperForEquipment {

    private ServerHelperForEquipment() {}

    public static void handleBlockBreakAction(
            ServerLevel level,
            ServerPlayer player,
            BlockPos pos,
            ServerboundPlayerActionPacket.Action action,
            Direction direction,
            CallbackInfo ci
    ) {
        if (action != ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK) {
            return;
        }

        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (stack.is(RegDurableItems.UNIVERSE_OMNI_BLADE)
                && stack.getOrDefault(RegComponentTypes.UNIVERSE_OMNI_BLADE_MODE, 0) == 0) {
            execShovelMode(level, player, direction, pos, ci);
        }
    }

    private static void execShovelMode(
            ServerLevel level,
            ServerPlayer player,
            Direction direction,
            BlockPos pos,
            CallbackInfo ci
    ) {
        if (direction == Direction.DOWN || !level.getBlockState(pos.above()).isAir()) {
            return;
        }

        BlockState state = level.getBlockState(pos);
        BlockState newState = null;

        if (state.is(Blocks.GRASS_BLOCK)
                || state.is(Blocks.DIRT)
                || state.is(Blocks.COARSE_DIRT)
                || state.is(Blocks.ROOTED_DIRT)) {
            newState = Blocks.DIRT_PATH.defaultBlockState();
        }

        if (newState == null) {
            return;
        }

        player.swing(InteractionHand.MAIN_HAND, true);
        level.playSound(null, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0f, 1.0f);
        level.setBlock(pos, newState, Block.UPDATE_ALL | Block.UPDATE_IMMEDIATE);
        ci.cancel();
    }

}
