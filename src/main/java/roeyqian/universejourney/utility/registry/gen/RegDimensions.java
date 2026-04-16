/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.utility.registry.gen;

// Minecraft
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;

public final class RegDimensions {

    public static final ResourceKey<Level> ORE_CONTINENT =
            ResourceKey.create(Registries.DIMENSION, GenRegHelper.id("ore_continent"));
    public static final ResourceKey<Level> HARVEST_CONTINENT =
            ResourceKey.create(Registries.DIMENSION, GenRegHelper.id("harvest_continent"));

    public static void init() {
        UniverseJourney.LOGGER.info("[Server] Registering 'RegDimensions'");
    }

}
