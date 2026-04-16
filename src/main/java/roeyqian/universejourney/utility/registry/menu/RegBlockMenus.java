/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.utility.registry.menu;

// Minecraft
import net.minecraft.world.inventory.MenuType;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;
import roeyqian.universejourney.menu.block.SupremeFurnaceMenu;
import roeyqian.universejourney.menu.block.SupremeReserverMenu;
import roeyqian.universejourney.menu.block.SupremeWorktableMenu;
import roeyqian.universejourney.menu.block.UniverseLibraryMenu;
import roeyqian.universejourney.menu.block.UniverseRefineryMenu;
import roeyqian.universejourney.menu.block.UniverseVoidPoolMenu;
import roeyqian.universejourney.menu.block.UniverseWorkstationMenu;

public final class RegBlockMenus {

    public static final MenuType<SupremeFurnaceMenu> SUPREME_FURNACE_HANDLER =
            MenuRegHelper.register("supreme_furnace", SupremeFurnaceMenu::new);
    public static final MenuType<SupremeWorktableMenu> SUPREME_WORKTABLE_HANDLER =
            MenuRegHelper.register("supreme_worktable", SupremeWorktableMenu::new);
    public static final MenuType<SupremeReserverMenu> SUPREME_RESERVER_HANDLER =
            MenuRegHelper.register("supreme_reserver", SupremeReserverMenu::new);

    public static final MenuType<UniverseWorkstationMenu> UNIVERSE_WORKSTATION_HANDLER =
            MenuRegHelper.register("universe_workstation", UniverseWorkstationMenu::new);
    public static final MenuType<UniverseRefineryMenu> UNIVERSE_REFINERY_HANDLER =
            MenuRegHelper.register("universe_refinery", UniverseRefineryMenu::new);
    public static final MenuType<UniverseVoidPoolMenu> UNIVERSE_VOID_POOL_HANDLER =
            MenuRegHelper.register("universe_void_pool", UniverseVoidPoolMenu::new);
    public static final MenuType<UniverseLibraryMenu> UNIVERSE_LIBRARY_HANDLER =
            MenuRegHelper.register("universe_library", UniverseLibraryMenu::new);

    public static void init() {
        UniverseJourney.LOGGER.info("[Server] Registering 'RegBlockMenus'");
    }

}
