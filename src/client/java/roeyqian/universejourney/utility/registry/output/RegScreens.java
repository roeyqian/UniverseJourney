/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.utility.registry.output;

// Minecraft
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;
import roeyqian.universejourney.utility.registry.block.RegActiveBlockEntities;
import roeyqian.universejourney.utility.registry.menu.RegItemMenus;
import roeyqian.universejourney.screen.block.SupremeFurnaceScreen;
import roeyqian.universejourney.screen.block.SupremeReserverScreen;
import roeyqian.universejourney.screen.block.SupremeWorktableScreen;
import roeyqian.universejourney.utility.registry.menu.RegBlockMenus;
import roeyqian.universejourney.render.block.UniverseLibraryRenderer;
import roeyqian.universejourney.screen.block.UniverseLibraryScreen;
import roeyqian.universejourney.screen.block.UniverseRefineryScreen;
import roeyqian.universejourney.screen.block.UniverseVoidPoolScreen;
import roeyqian.universejourney.screen.block.UniverseWorkstationScreen;
import roeyqian.universejourney.screen.item.UniverseConsoleScreen;

public final class RegScreens {

    public static void init() {
        MenuScreens.register(RegBlockMenus.SUPREME_FURNACE_HANDLER, SupremeFurnaceScreen::new);
        MenuScreens.register(RegBlockMenus.SUPREME_WORKTABLE_HANDLER, SupremeWorktableScreen::new);
        MenuScreens.register(RegBlockMenus.SUPREME_RESERVER_HANDLER, SupremeReserverScreen::new);

        MenuScreens.register(RegItemMenus.UNIVERSE_CONSOLE_HANDLER, UniverseConsoleScreen::new);
        MenuScreens.register(RegBlockMenus.UNIVERSE_WORKSTATION_HANDLER, UniverseWorkstationScreen::new);
        MenuScreens.register(RegBlockMenus.UNIVERSE_REFINERY_HANDLER, UniverseRefineryScreen::new);
        MenuScreens.register(RegBlockMenus.UNIVERSE_LIBRARY_HANDLER, UniverseLibraryScreen::new);
        MenuScreens.register(RegBlockMenus.UNIVERSE_VOID_POOL_HANDLER, UniverseVoidPoolScreen::new);

        BlockEntityRenderers.register(RegActiveBlockEntities.UNIVERSE_LIBRARY_ENTITY, UniverseLibraryRenderer::new);

        UniverseJourney.LOGGER.info("[Client] Registering 'RegScreens'");
    }
}