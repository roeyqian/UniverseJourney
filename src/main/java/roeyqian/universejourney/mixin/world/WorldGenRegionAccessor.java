/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.mixin.world;

// Minecraft
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;

// Sponge Powered Mixin
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = WorldGenRegion.class, priority = 240000)
public interface WorldGenRegionAccessor {

    @Accessor("level")
    ServerLevel getWorld();

}
