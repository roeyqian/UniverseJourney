/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.block;

// Fabric
import net.fabricmc.fabric.api.event.player.UseBlockCallback;

// Minecraft
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.shapes.VoxelShape;

// Java Standard
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

// Universe Journey
import roeyqian.universejourney.utility.registry.item.RegDurableItems;

public interface CustomPortalBlock {

    int TELEPORT_TICKS = 80;
    EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;

    VoxelShape X_SHAPE = Block.box(
            0.0, 0.0, 6.0, 16.0, 16.0, 10.0
    );
    VoxelShape Z_SHAPE = Block.box(
            6.0, 0.0, 0.0, 10.0, 16.0, 16.0
    );

    static VoxelShape getOutlineShape(
            BlockState state
    ) {
        return state.getValue(AXIS) == Direction.Axis.Z ? Z_SHAPE : X_SHAPE;
    }

    static boolean isValidPortal(
            LevelReader world,
            BlockPos pos,
            Direction.Axis axis,
            Block frameBlock,
            Block portalBlock
    ) {
        Direction leftDir = (axis == Direction.Axis.X) ? Direction.WEST : Direction.NORTH;
        Direction rightDir = leftDir.getOpposite();
        Direction upDir = Direction.UP;
        Direction downDir = Direction.DOWN;

        int left = countPortalBlocks(world, pos, leftDir, portalBlock);
        int right = countPortalBlocks(world, pos, rightDir, portalBlock);
        int down = countPortalBlocks(world, pos, downDir, portalBlock);
        int up = countPortalBlocks(world, pos, upDir, portalBlock);

        BlockPos bottomLeft = pos.relative(leftDir, left).relative(downDir, down);
        BlockPos topRight = pos.relative(rightDir, right).relative(upDir, up);

        int width = (axis == Direction.Axis.X)
                ? topRight.getX() - bottomLeft.getX() + 1
                : topRight.getZ() - bottomLeft.getZ() + 1;
        int height = topRight.getY() - bottomLeft.getY() + 1;

        if (width < 2 || width > 21 || height < 3 || height > 21) return false;

        return checkFrameLine(
                world,
                bottomLeft.below(),
                rightDir,
                width,
                frameBlock
        ) && checkFrameLine(
                world,
                bottomLeft.above(height),
                rightDir,
                width,
                frameBlock
        ) && checkFrameLine(
                world,
                bottomLeft.relative(leftDir, 1),
                Direction.UP,
                height,
                frameBlock
        ) && checkFrameLine(
                world,
                topRight.relative(rightDir, 1),
                Direction.UP,
                height,
                frameBlock
        );
    }

    static boolean shouldBreakPortal(
            Block portalBlock,
            Block frameBlock,
            BlockState state,
            BlockState neighborState,
            BlockPos pos,
            Direction direction,
            LevelReader world
    ) {
        Direction.Axis currentAxis = state.getValue(AXIS);
        Direction.Axis neighborAxis = direction.getAxis();

        return !(currentAxis != neighborAxis
                && neighborAxis.isHorizontal())
                && !neighborState.is(portalBlock)
                && !isValidPortal(world, pos, currentAxis, frameBlock, portalBlock);
    }

    static void handleEntityCollision(
            Level world,
            BlockPos pos,
            Entity entity,
            Map<UUID, Integer> portalTicks,
            Set<UUID> inPortalThisTick,
            boolean[] clientInPortalFlag,
            Block frameBlock,
            Block portalBlock,
            ResourceKey<Level> sourceDim,
            ResourceKey<Level> targetDim
    ) {
        if (world.isClientSide()) {
            if (entity instanceof Player && clientInPortalFlag != null) clientInPortalFlag[0] = true;
            return;
        }

        if (!(entity instanceof ServerPlayer player)) return;
        if (player.isPassenger() || player.isVehicle()) return;
        if (!player.canUsePortal(false)) return;

        UUID uuid = player.getUUID();

        if (player.isOnPortalCooldown()) {
            player.setPortalCooldown(20);
            portalTicks.remove(uuid);
            return;
        }

        if (player.isCreative()) {
            player.setPortalCooldown(80);
            execTeleport(player, pos, frameBlock, portalBlock, sourceDim, targetDim);
            return;
        }

        if (!inPortalThisTick.add(uuid)) return;

        int ticks = portalTicks.merge(uuid, 1, Integer::sum);
        if (ticks == 1) {
            world.playSound(
                    null,
                    pos,
                    SoundEvents.PORTAL_TRIGGER,
                    SoundSource.PLAYERS,
                    0.2F,
                    1.0F
            );
        } else if (ticks >= TELEPORT_TICKS) {
            portalTicks.remove(uuid);
            inPortalThisTick.remove(uuid);
            player.setPortalCooldown(60);
            execTeleport(player, pos, frameBlock, portalBlock, sourceDim, targetDim);
        }
    }

    static void execTeleport(
            ServerPlayer player,
            BlockPos portalPos,
            Block frameBlock,
            Block portalBlock,
            ResourceKey<Level> sourceDim,
            ResourceKey<Level> targetDim
    ) {
        MinecraftServer server = player.level().getServer();
        ServerLevel currentWorld = player.level();
        ServerLevel targetWorld;

        BlockState currentState = currentWorld.getBlockState(portalPos);
        Direction.Axis portalAxis = currentState.hasProperty(AXIS)
                ? currentState.getValue(AXIS)
                : Direction.Axis.X;

        if (currentWorld.dimension() == sourceDim) {
            targetWorld = server.getLevel(targetDim);
        } else {
            targetWorld = server.getLevel(sourceDim);
        }

        if (targetWorld == null) return;

        BlockPos targetPos = findOrCreatePortal(
                targetWorld,
                portalPos,
                portalAxis,
                frameBlock,
                portalBlock
        );

        player.teleportTo(
                targetWorld,
                targetPos.getX() + 0.5,
                targetPos.getY(),
                targetPos.getZ() + 0.5,
                Set.of(),
                player.getYRot(),
                player.getXRot(),
                false
        );
        player.setPortalCooldown(80);
        targetWorld.playSound(
                null, player.blockPosition(),
                SoundEvents.PORTAL_TRAVEL, SoundSource.PLAYERS, 0.2F, 1.0F
        );
    }

    static BlockPos findOrCreatePortal(
            ServerLevel targetWorld,
            BlockPos sourcePos,
            Direction.Axis axis,
            Block frameBlock,
            Block portalBlock
    ) {
        BlockPos existing = findExistingPortal(
                targetWorld,
                sourcePos.getX(),
                sourcePos.getZ(),
                portalBlock
        );
        if (existing != null) return existing;

        int surfaceY = targetWorld.getHeight(
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                sourcePos.getX(), sourcePos.getZ()
        );
        BlockPos buildPos = new BlockPos(sourcePos.getX(), surfaceY, sourcePos.getZ());
        return buildPortalAt(targetWorld, buildPos, axis, frameBlock, portalBlock);
    }

    static BlockPos buildPortalAt(
            ServerLevel world,
            BlockPos groundPos,
            Direction.Axis axis,
            Block frameBlock,
            Block portalBlock
    ) {
        for (int y = 0; y < 5; y++) {
            for (int i = -1; i <= 2; i++) {
                BlockPos current = (axis == Direction.Axis.X)
                        ? groundPos.offset(i, y, 0)
                        : groundPos.offset(0, y, i);
                if (y == 0 || y == 4 || i == -1 || i == 2) {
                    world.setBlockAndUpdate(current, frameBlock.defaultBlockState());
                } else {
                    world.setBlockAndUpdate(current, Blocks.AIR.defaultBlockState());
                }
            }
        }

        List<BlockPos> portalPositions = new ArrayList<>();
        for (int y = 1; y <= 3; y++) {
            for (int i = 0; i <= 1; i++) {
                BlockPos pos = (axis == Direction.Axis.X)
                        ? groundPos.offset(i, y, 0)
                        : groundPos.offset(0, y, i);
                portalPositions.add(pos);
                world.setBlock(pos, portalBlock.defaultBlockState().setValue(AXIS, axis), 18);
            }
        }

        for (int y = 0; y < 5; y++) {
            for (int i = 0; i <= 1; i++) {
                BlockPos front = (axis == Direction.Axis.X)
                        ? groundPos.offset(i, y, 1)
                        : groundPos.offset(1, y, i);
                BlockPos back = (axis == Direction.Axis.X)
                        ? groundPos.offset(i, y, -1)
                        : groundPos.offset(-1, y, i);
                if (!world.getBlockState(front).isAir()) world.setBlockAndUpdate(front, Blocks.AIR.defaultBlockState());
                if (!world.getBlockState(back).isAir()) world.setBlockAndUpdate(back, Blocks.AIR.defaultBlockState());
            }
        }

        for (BlockPos pos : portalPositions) world.updateNeighborsAt(pos, portalBlock);
        return groundPos.above(1);
    }

    static void registerPortalIgniter(
            Block frameBlock,
            Block portalBlock,
            Predicate<Level> dimensionPredicate
    ) {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            BlockPos portalPos = hitResult.getBlockPos().relative(hitResult.getDirection());
            ItemStack stack = player.getItemInHand(hand);

            boolean isNotFlint = !stack.is(Items.FLINT_AND_STEEL);
            boolean isNotUniverseSword = !stack.is(RegDurableItems.UNIVERSE_ULTIMA_SWORD);
            if (isNotFlint && isNotUniverseSword) return InteractionResult.PASS;

            player.swing(hand);
            if (world.isClientSide()) return InteractionResult.PASS;
            if (!dimensionPredicate.test(world)) return InteractionResult.PASS;

            boolean xLight = tryLightPortal(
                    world,
                    portalPos,
                    Direction.Axis.X,
                    frameBlock,
                    portalBlock
            );
            boolean zLight = tryLightPortal(
                    world,
                    portalPos,
                    Direction.Axis.Z,
                    frameBlock,
                    portalBlock
            );
            if (xLight || zLight) {
                if (isNotFlint) {
                    world.playSound(
                            null,
                            portalPos,
                            SoundEvents.FLINTANDSTEEL_USE,
                            SoundSource.BLOCKS,
                            0.8F,
                            1.0F
                    );
                }
                if (!player.isCreative()) {
                    stack.hurtAndBreak(
                            1, player,
                            hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND
                    );
                }
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        });
    }

    static boolean tryLightPortal(
            Level world,
            BlockPos clickPos,
            Direction.Axis axis,
            Block frameBlock,
            Block portalBlock
    ) {
        final int MIN_WIDTH = 2;
        final int MAX_WIDTH = 21;
        final int MIN_HEIGHT = 3;
        final int MAX_HEIGHT = 21;

        Direction left = (axis == Direction.Axis.X) ? Direction.WEST : Direction.NORTH;
        Direction right = (axis == Direction.Axis.X) ? Direction.EAST : Direction.SOUTH;

        BlockPos bottom = clickPos;
        for (int i = 0; i < MAX_HEIGHT; i++) {
            if (!isInnerBlock(world, bottom.below(), portalBlock)) break;
            bottom = bottom.below();
        }
        if (isNotFrame(world, bottom.below(), frameBlock)) return false;

        BlockPos bottomLeft = bottom;
        for (int i = 0; i < MAX_WIDTH; i++) {
            if (!isInnerBlock(world, bottomLeft.relative(left), portalBlock)) break;
            bottomLeft = bottomLeft.relative(left);
        }
        if (isNotFrame(world, bottomLeft.relative(left), frameBlock)) return false;

        int width = 0;
        {
            BlockPos probe = bottomLeft;
            while (width < MAX_WIDTH && isInnerBlock(world, probe, portalBlock)) {
                width++;
                probe = probe.relative(right);
            }
            if (width < MIN_WIDTH || width > MAX_WIDTH) return false;
            if (isNotFrame(world, probe, frameBlock)) return false;
        }

        int height = 0;
        {
            BlockPos probe = bottomLeft;
            while (height < MAX_HEIGHT && isInnerBlock(world, probe, portalBlock)) {
                height++;
                probe = probe.above();
            }
            if (height < MIN_HEIGHT || height > MAX_HEIGHT) return false;
            if (isNotFrame(world, probe, frameBlock)) return false;
        }

        return validateAndFillPortal(
                world,
                bottomLeft,
                axis,
                left,
                right,
                width,
                height,
                frameBlock,
                portalBlock
        );
    }

    private static int countPortalBlocks(
            LevelReader world,
            BlockPos pos,
            Direction dir,
            Block portalBlock
    ) {
        int count = 0;
        while (world.getBlockState(pos.relative(dir, count + 1)).getBlock() == portalBlock) count++;
        return count;
    }

    private static boolean checkFrameLine(
            LevelReader world,
            BlockPos startPos,
            Direction moveDir,
            int length,
            Block frameBlock
    ) {
        for (int i = 0; i < length; i++) {
            if (!world.getBlockState(startPos.relative(moveDir, i)).is(frameBlock)) return false;
        }
        return true;
    }

    private static BlockPos findExistingPortal(
            ServerLevel world,
            int centerX,
            int centerZ,
            Block portalBlock
    ) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int x = -16; x <= 16; x++) {
            for (int z = -16; z <= 16; z++) {
                for (int y = world.getMinY(); y <= world.getMaxY(); y++) {
                    mutable.set(centerX + x, y, centerZ + z);
                    if (world.getBlockState(mutable).getBlock() == portalBlock) return mutable.immutable();
                }
            }
        }
        return null;
    }

    private static boolean validateAndFillPortal(
            Level world,
            BlockPos bottomLeft,
            Direction.Axis axis,
            Direction left,
            Direction right,
            int width,
            int height,
            Block frameBlock,
            Block portalBlock
    ) {
        for (int y = 0; y < height; y++) {
            if (isNotFrame(world, bottomLeft.relative(left).above(y), frameBlock)) return false;
            if (isNotFrame(world, bottomLeft.relative(right, width).above(y), frameBlock)) return false;

            for (int x = 0; x < width; x++) {
                if (!isInnerBlock(world, bottomLeft.relative(right, x).above(y), portalBlock)) return false;
            }
        }

        for (int x = 0; x < width; x++) {
            if (isNotFrame(world, bottomLeft.relative(right, x).below(), frameBlock)) return false;
        }

        for (int x = 0; x < width; x++) {
            if (isNotFrame(world, bottomLeft.relative(right, x).above(height), frameBlock)) return false;
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                world.setBlockAndUpdate(
                        bottomLeft.relative(right, x).above(y),
                        portalBlock.defaultBlockState().setValue(AXIS, axis)
                );
            }
        }

        return true;
    }

    private static boolean isInnerBlock(
            Level world, BlockPos pos, Block portalBlock
    ) {
        BlockState state = world.getBlockState(pos);
        return state.isAir() || state.is(Blocks.FIRE) || state.getBlock() == portalBlock;
    }

    private static boolean isNotFrame(
            Level world, BlockPos pos, Block frameBlock
    ) {
        return !world.getBlockState(pos).is(frameBlock);
    }

}