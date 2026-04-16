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
import roeyqian.universejourney.item.durable.UniverseConsole;
import roeyqian.universejourney.menu.item.UniverseConsoleMenu;

public final class RegItemMenus {

    public static final MenuType<UniverseConsoleMenu> UNIVERSE_CONSOLE_HANDLER =
            MenuRegHelper.registerExtended(
                    "universe_console",
                    (syncId, _, boundBlocks) -> new UniverseConsoleMenu(syncId, boundBlocks),
                    UniverseConsole.BoundBlockList.PACKET_CODEC
            );

    public static void init() {
        UniverseJourney.LOGGER.info("[Server] Registering 'RegItemMenus'");
    }

}
