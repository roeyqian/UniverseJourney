/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.mixin.world;

// Minecraft
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;

// Sponge Powered Mixin
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

// Java Standard
import java.util.List;

@Mixin(value = ShapelessRecipe.class, priority = 240000)
public interface ShapelessRecipeAccessor {

    @Accessor("ingredients")
    List<Ingredient> getIngredients();

}
