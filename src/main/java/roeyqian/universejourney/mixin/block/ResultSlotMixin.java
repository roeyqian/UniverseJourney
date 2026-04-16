/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.mixin.block;

// Minecraft
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

// Sponge Powered Mixin
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Universe Journey
import roeyqian.universejourney.utility.mixin.block.BlockHelperForFunction;

@Mixin(value = ResultSlot.class, priority = 240000)
public abstract class ResultSlotMixin extends Slot {

    @Shadow @Final
    private CraftingContainer craftSlots;

    @Shadow
    protected abstract void checkTakeAchievements(ItemStack stack);

    public ResultSlotMixin(
            Container inventory,
            int index,
            int x,
            int y
    ) {
        super(inventory, index, x, y);
    }

    /* Universe Workstation & Supreme Worktable: Item Deduction
    */
    @Inject(method = "onTake", at = @At("HEAD"), cancellable = true)
    private void inOnTakeItem(
            Player player,
            ItemStack stack,
            CallbackInfo ci
    ) {
        BlockHelperForFunction.handleOnTake(player, this.craftSlots, stack, this::checkTakeAchievements, ci);
    }

}
