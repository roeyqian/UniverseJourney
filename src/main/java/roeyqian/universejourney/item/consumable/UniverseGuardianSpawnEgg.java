/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.item.consumable;

// Minecraft
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.SpawnEggItem;

// Universe Journey
import roeyqian.universejourney.item.CustomItemSettings;
import roeyqian.universejourney.utility.registry.entity.RegLiveEntities;

public class UniverseGuardianSpawnEgg extends SpawnEggItem {

    public UniverseGuardianSpawnEgg(
            Properties settings
    ) {
        super(applySettings(settings));
    }

    private static Properties applySettings(
            Properties settings
    ) {
        return CustomItemSettings.applyUniverseDefaults(settings)
                .spawnEgg(RegLiveEntities.UNIVERSE_GUARDIAN)
                .component(
                        DataComponents.LORE,
                        CustomItemSettings.universeLore("universe_guardian_spawn_egg", 2)
                );
    }

}
