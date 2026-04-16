/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.mixin.menu;

// Minecraft
import net.minecraft.world.Container;
import net.minecraft.world.inventory.EnchantmentMenu;

// Sponge Powered Mixin
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Universe Journey
import roeyqian.universejourney.utility.mixin.menu.MenuHelperForEquipment;

@Mixin(value = EnchantmentMenu.class, priority = 240000)
public class EnchantmentMenuMixin {

    /* Universe Armors: Unenchantable
    */
    @Inject(method = "slotsChanged", at = @At("HEAD"), cancellable = true)
    private void inOnContentChanged(
            Container inventory,
            CallbackInfo ci
    ) {
        MenuHelperForEquipment.handleSlotsChanged(inventory, ci);
    }

}
