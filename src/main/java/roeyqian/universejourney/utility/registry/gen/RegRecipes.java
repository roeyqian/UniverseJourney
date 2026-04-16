/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.utility.registry.gen;

// Minecraft
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;
import roeyqian.universejourney.gen.recipe.SupremeCookingRecipe;
import roeyqian.universejourney.gen.recipe.SupremeCraftingRecipe;
import roeyqian.universejourney.gen.recipe.UniverseCraftingRecipe;
import roeyqian.universejourney.gen.recipe.UniverseCookingRecipe;

public final class RegRecipes {

    public static final RecipeType<CraftingRecipe> SUPREME_CRAFTING_TYPE =
            GenRegHelper.registerRecipeType("supreme_crafting");
    public static final RecipeType<CraftingRecipe> UNIVERSE_CRAFTING_TYPE =
            GenRegHelper.registerRecipeType("universe_crafting");
    public static final RecipeType<SupremeCookingRecipe> SUPREME_COOKING_TYPE =
            GenRegHelper.registerRecipeType("supreme_cooking");
    public static final RecipeType<UniverseCookingRecipe> UNIVERSE_COOKING_TYPE =
            GenRegHelper.registerRecipeType("universe_cooking");

    public static final RecipeSerializer<SupremeCraftingRecipe> SUPREME_CRAFTING_SERIALIZER =
            GenRegHelper.registerRecipeSerializer("supreme_crafting", SupremeCraftingRecipe.SERIALIZER);
    public static final RecipeSerializer<UniverseCraftingRecipe> UNIVERSE_CRAFTING_SERIALIZER =
            GenRegHelper.registerRecipeSerializer("universe_crafting", UniverseCraftingRecipe.SERIALIZER);
    public static final RecipeSerializer<SupremeCookingRecipe> SUPREME_COOKING_SERIALIZER =
            GenRegHelper.registerRecipeSerializer("supreme_cooking", SupremeCookingRecipe.SERIALIZER);
    public static final RecipeSerializer<UniverseCookingRecipe> UNIVERSE_COOKING_SERIALIZER =
            GenRegHelper.registerRecipeSerializer("universe_cooking", UniverseCookingRecipe.SERIALIZER);

    public static final RecipeBookCategory UNIVERSE_CRAFTING =
            GenRegHelper.registerRecipeBookCategory("universe_crafting");
    public static final RecipeBookCategory SUPREME_CRAFTING =
            GenRegHelper.registerRecipeBookCategory("supreme_crafting");
    public static final RecipeBookCategory SUPREME_COOKING =
            GenRegHelper.registerRecipeBookCategory("supreme_cooking");
    public static final RecipeBookCategory UNIVERSE_COOKING =
            GenRegHelper.registerRecipeBookCategory("universe_cooking");

    public static void init() {

        UniverseJourney.LOGGER.info("[Server] Registering 'RegRecipes'");
    }

}
