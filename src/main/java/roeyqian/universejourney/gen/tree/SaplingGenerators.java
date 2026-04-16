/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.gen.tree;

// Minecraft
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.grower.TreeGrower;

// Java Standard
import java.util.Optional;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;

public class SaplingGenerators {

    public static final TreeGrower GOLDEN = new TreeGrower(
            "golden",
            Optional.empty(),
            Optional.of(
                    ResourceKey.create(
                            Registries.CONFIGURED_FEATURE,
                            Identifier.fromNamespaceAndPath(UniverseJourney.MOD_ID, "golden_tree")
                    )
            ),
            Optional.empty()
    );

}
