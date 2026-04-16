/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.mixin.world;

// Minecraft
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;

// Sponge Powered Mixin
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Universe Journey
import roeyqian.universejourney.utility.mixin.world.WorldHelperForDimension;

@Mixin(value = NoiseBasedChunkGenerator.class, priority = 240000)
public class NoiseChunkGeneratorMixin {

    @Inject(method = "buildSurface(Lnet/minecraft/server/level/WorldGenRegion;"
                    + "Lnet/minecraft/world/level/StructureManager;"
                    + "Lnet/minecraft/world/level/levelgen/RandomState;"
                    + "Lnet/minecraft/world/level/chunk/ChunkAccess;)V",
            at = @At("RETURN"))
    private void onBuildSurface(
            WorldGenRegion region,
            StructureManager structureAccessor,
            RandomState noiseConfig,
            ChunkAccess chunk,
            CallbackInfo ci
    ) {
        WorldHelperForDimension.handleBuildSurface(
                (NoiseBasedChunkGenerator) (Object) this,
                region, noiseConfig, chunk
        );
    }

}
