/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.utility.mixin.block;

// Minecraft
import net.minecraft.recipebook.PlaceRecipeHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;

// Sponge Powered Mixin
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Universe Journey
import roeyqian.universejourney.gen.recipe.SupremeCraftingRecipe;
import roeyqian.universejourney.gen.recipe.UniverseCraftingRecipe;
import roeyqian.universejourney.utility.registry.gen.RegRecipes;

public final class BlockHelperForFunction {

    private BlockHelperForFunction() {}

    public static void handleOnTake(
            Player player,
            CraftingContainer craftSlots,
            ItemStack stack,
            AchievementConsumer achievementConsumer,
            CallbackInfo ci
    ) {
        if (player.level().isClientSide()) {
            return;
        }

        var world = player.level();
        var recipeManager = world.recipeAccess().getSynchronizedRecipes();
        CraftingInput recipeInput = craftSlots.asCraftInput();

        var supremeMatch = recipeManager.getFirstMatch(RegRecipes.SUPREME_CRAFTING_TYPE, recipeInput, world);
        var universeMatch = recipeManager.getFirstMatch(RegRecipes.UNIVERSE_CRAFTING_TYPE, recipeInput, world);

        if (supremeMatch.isEmpty() && universeMatch.isEmpty()) {
            return;
        }

        for (int i = 0; i < craftSlots.getContainerSize(); ++i) {
            ItemStack inputStack = craftSlots.getItem(i);
            if (!inputStack.isEmpty()) {
                inputStack.shrink(1);
            }
        }

        achievementConsumer.accept(stack);
        ci.cancel();
    }

    public static <T> boolean handlePlaceRecipe(
            int width,
            int height,
            Recipe<?> recipe,
            Iterable<T> slots,
            PlaceRecipeHelper.Output<T> filler
    ) {
        if (recipe instanceof SupremeCraftingRecipe supremeRecipe) {
            PlaceRecipeHelper.placeRecipe(
                    width, height,
                    supremeRecipe.getWidth(), supremeRecipe.getHeight(),
                    slots, filler
            );
            return true;
        }

        if (recipe instanceof UniverseCraftingRecipe universeRecipe) {
            PlaceRecipeHelper.placeRecipe(
                    width, height,
                    universeRecipe.getWidth(), universeRecipe.getHeight(),
                    slots, filler
            );
            return true;
        }

        return false;
    }

    @FunctionalInterface
    public interface AchievementConsumer {
        void accept(ItemStack stack);
    }

}
