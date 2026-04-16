/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.utility.registry.gen;

// Minecraft
import net.minecraft.resources.Identifier;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;
import roeyqian.universejourney.gen.biome.HarvestContinentBiomeSource;

public final class RegBiomeSources {

    public static final Identifier HARVEST_CONTINENT = GenRegHelper.id("harvest_continent");

    private RegBiomeSources() {}

    public static void init() {
        GenRegHelper.registerBiomeSource(
                HARVEST_CONTINENT.getPath(),
                HarvestContinentBiomeSource.CODEC
        );
        UniverseJourney.LOGGER.info("[Server] Registering 'RegBiomeSources'");
    }

}
