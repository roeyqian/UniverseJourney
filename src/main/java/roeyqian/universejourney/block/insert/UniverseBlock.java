/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.block.insert;

// Minecraft
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

// JetBrains Specify
import org.jspecify.annotations.NonNull;

public class UniverseBlock extends Block {

    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    private static final int HALF_X = 32;
    private static final int HALF_Z = 32;
    private static final int HALF_Y = 16;
    private static final int STEP = 2;

    public UniverseBlock(
            BlockBehaviour.Properties settings
    ) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder
    ) {
        builder.add(LIT);
    }

    @Override @NonNull
    protected InteractionResult useWithoutItem(
            @NonNull BlockState state,
            Level world,
            @NonNull BlockPos pos,
            @NonNull Player player,
            @NonNull BlockHitResult hit
    ) {
        if (!world.isClientSide()) {
            boolean isLit = state.getValue(LIT);
            world.setBlockAndUpdate(pos, state.setValue(LIT, !isLit));

            if (!isLit) spawnLightNodes(world, pos);
            else removeLightNodes(world, pos);
        }
        return InteractionResult.SUCCESS;
    }

    private void spawnLightNodes(
            Level world, BlockPos pos
    ) {
        for (int x = -HALF_X; x <= HALF_X; x += STEP) {
            for (int y = -HALF_Y; y <= HALF_Y; y += STEP) {
                for (int z = -HALF_Z; z <= HALF_Z; z += STEP) {
                    BlockPos targetPos = pos.offset(x, y, z);
                    if (world.getBlockState(targetPos).isAir()) {
                        world.setBlock(
                                targetPos,
                                Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, 15),
                                Block.UPDATE_CLIENTS);
                    }
                }
            }
        }
    }

    @Override
    protected void affectNeighborsAfterRemoval(
            BlockState state,
            ServerLevel world,
            @NonNull BlockPos pos,
            boolean moved
    ) {
        BlockState newState = world.getBlockState(pos);
        if (!state.is(newState.getBlock())) {
            removeLightNodes(world, pos);
            super.affectNeighborsAfterRemoval(state, world, pos, moved);
        }
    }

    private void removeLightNodes(
            Level world,
            BlockPos pos
    ) {
        for (int x = -HALF_X - 1; x <= HALF_X + 1; x++) {
            for (int y = -HALF_Y - 1; y <= HALF_Y + 1; y++) {
                for (int z = -HALF_Z - 1; z <= HALF_Z + 1; z++) {
                    BlockPos targetPos = pos.offset(x, y, z);
                    if (world.getBlockState(targetPos).is(Blocks.LIGHT)) {
                        world.setBlock(targetPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
                    }
                }
            }
        }
    }

}
