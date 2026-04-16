/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.utility.registry.block;

// Minecraft
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;
import roeyqian.universejourney.block.active.SupremeFurnace;
import roeyqian.universejourney.block.active.SupremeReserver;
import roeyqian.universejourney.block.active.SupremeWorktable;
import roeyqian.universejourney.block.active.UniverseLibrary;
import roeyqian.universejourney.block.active.UniverseRefinery;
import roeyqian.universejourney.block.active.UniverseVoidPool;
import roeyqian.universejourney.block.active.UniverseWorkstation;

public final class RegActiveBlocks {

    private static final String supreme = "supreme";
    private static final String universe = "universe";

    public static final Block SUPREME_RESERVER = BlockRegHelper.registerBase(
            "supreme_reserver",
            supreme,
            SupremeReserver::new,
            BlockBehaviour.Properties.of()
    );
    public static final Block SUPREME_FURNACE = BlockRegHelper.registerBase(
            "supreme_furnace",
            supreme,
            SupremeFurnace::new,
            BlockBehaviour.Properties.of()
    );
    public static final Block SUPREME_WORKTABLE = BlockRegHelper.registerBase(
            "supreme_worktable",
            supreme,
            SupremeWorktable::new,
            BlockBehaviour.Properties.of()
    );

    public static final Block UNIVERSE_WORKSTATION = BlockRegHelper.registerBase(
            "universe_workstation",
            universe,
            UniverseWorkstation::new,
            BlockBehaviour.Properties.of()
    );
    public static final Block UNIVERSE_REFINERY = BlockRegHelper.registerBase(
            "universe_refinery",
            universe,
            UniverseRefinery::new,
            BlockBehaviour.Properties.of()
    );
    public static final Block UNIVERSE_VOID_POOL = BlockRegHelper.registerBase(
            "universe_void_pool",
            universe,
            UniverseVoidPool::new,
            BlockBehaviour.Properties.of()
    );
    public static final Block UNIVERSE_LIBRARY = BlockRegHelper.registerBase(
            "universe_library",
            universe,
            UniverseLibrary::new,
            BlockBehaviour.Properties.of()
    );

    public static void init() {
        UniverseJourney.LOGGER.info("[Server] Registering 'RegActiveBlocks'");
    }

}
