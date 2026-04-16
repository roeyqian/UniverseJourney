/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.item.durable;

// Minecraft
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;

// Universe Journey
import roeyqian.universejourney.item.CustomItemSettings;

public class UniverseBoots extends Item {

    public UniverseBoots(
            Properties settings
    ) {
        super(applySettings(settings));
    }

    private static Properties applySettings(
            Properties settings
    ) {
        return CustomItemSettings.applyUniverseDefaults(settings).component(
                DataComponents.LORE, CustomItemSettings.universeLore("universe_boots", 3)
        );
    }

}
