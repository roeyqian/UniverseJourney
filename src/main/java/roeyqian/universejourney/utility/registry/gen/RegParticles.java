/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.utility.registry.gen;

// Minecraft
import net.minecraft.core.particles.SimpleParticleType;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;

public final class RegParticles {

    public static final SimpleParticleType UNIVERSE_SONIC_BOOM =
            GenRegHelper.registerSimpleParticle("universe_sonic_boom");

    public static void init() {
        UniverseJourney.LOGGER.info("[Server] Registering 'RegParticles'");
    }

}
