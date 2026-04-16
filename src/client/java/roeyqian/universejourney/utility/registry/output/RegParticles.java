/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.utility.registry.output;

// Minecraft
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;
import roeyqian.universejourney.render.particle.UniverseSonicBoomParticle;

public final class RegParticles {

    public static void init() {
        ParticleProviderRegistry.getInstance().register(
                roeyqian.universejourney.utility.registry.gen.RegParticles.UNIVERSE_SONIC_BOOM,
                UniverseSonicBoomParticle.RainbowFactory::new
        );

        UniverseJourney.LOGGER.info("[Client] Registering 'RegParticles'");
    }

}
