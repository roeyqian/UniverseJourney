/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.mixin.block;

// Minecraft
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

// Sponge Powered Mixin
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Universe Journey
import roeyqian.universejourney.utility.mixin.block.BlockHelperForFarming;

@Mixin(value = CropBlock.class, priority = 240000)
public class CropBlockMixin {

    /* Ever-Water Farmland: Ability of Growing Crops
    */
    @Inject(method = "mayPlaceOn", at = @At("HEAD"), cancellable = true)
    protected void inCanPlantOnTop(
            BlockState floor,
            BlockGetter world,
            BlockPos pos,
            CallbackInfoReturnable<Boolean> cir
    ) {
        BlockHelperForFarming.handleMayPlaceOn(floor, cir);
    }

}
