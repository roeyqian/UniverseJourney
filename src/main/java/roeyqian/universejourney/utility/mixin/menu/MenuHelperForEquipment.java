/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.utility.mixin.menu;

// Minecraft
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

// Sponge Powered Mixin
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Universe Journey
import roeyqian.universejourney.item.RemoteAccessManager;
import roeyqian.universejourney.item.durable.UniverseBoots;
import roeyqian.universejourney.item.durable.UniverseChestplate;
import roeyqian.universejourney.item.durable.UniverseHelmet;
import roeyqian.universejourney.item.durable.UniverseLeggings;

public final class MenuHelperForEquipment {

    private MenuHelperForEquipment() {}

    public static void handleRemoved(
            Player player
    ) {
        RemoteAccessManager.endRemoteAccess(player);
    }

    public static void handleSlotsChanged(
            Container inventory,
            CallbackInfo ci
    ) {
        ItemStack itemStack = inventory.getItem(0);

        if (itemStack.getItem() instanceof UniverseHelmet
                || itemStack.getItem() instanceof UniverseChestplate
                || itemStack.getItem() instanceof UniverseLeggings
                || itemStack.getItem() instanceof UniverseBoots) {
            ci.cancel();
        }
    }
}
