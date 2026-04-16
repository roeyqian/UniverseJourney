/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.gen.recipe;

// Mojang
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

// Minecraft
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapedCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

// JetBrains Specify
import org.jspecify.annotations.NonNull;

// Java Standard
import java.util.List;

// Universe Journey
import roeyqian.universejourney.utility.registry.block.RegActiveBlocks;
import roeyqian.universejourney.utility.registry.gen.RegRecipes;

public class SupremeCraftingRecipe implements CraftingRecipe {

    public static final MapCodec<SupremeCraftingRecipe> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.STRING
                            .optionalFieldOf("group", "")
                            .forGetter(recipe -> recipe.recipeGroup),

                    CraftingBookCategory.CODEC
                            .fieldOf("category")
                            .orElse(CraftingBookCategory.MISC)
                            .forGetter(recipe -> recipe.recipeCategory),

                    ShapedRecipePattern.MAP_CODEC
                            .forGetter(recipe -> recipe.rawContents),

                    ItemStackTemplate.CODEC
                            .fieldOf("result")
                            .forGetter(recipe -> recipe.resultStack),

                    Codec.BOOL
                            .optionalFieldOf("show_notification", true)
                            .forGetter(recipe -> recipe.notification)
            ).apply(instance, SupremeCraftingRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, SupremeCraftingRecipe> PACKET_CODEC =
            StreamCodec.of(SupremeCraftingRecipe::write, SupremeCraftingRecipe::read);
    public static final RecipeSerializer<SupremeCraftingRecipe> SERIALIZER =
            new RecipeSerializer<>(CODEC, PACKET_CODEC);

    final String recipeGroup;
    final CraftingBookCategory recipeCategory;
    final ShapedRecipePattern rawContents;
    final ItemStackTemplate resultStack;
    final boolean notification;

    public SupremeCraftingRecipe(
            String group,
            CraftingBookCategory category,
            ShapedRecipePattern raw,
            ItemStackTemplate result,
            boolean showNotification
    ) {
        this.recipeGroup = "";
        this.recipeCategory = category;
        this.rawContents = raw;
        this.resultStack = result;
        this.notification = showNotification;
    }

    public int getWidth() {
        return this.rawContents.width();
    }

    public int getHeight() {
        return this.rawContents.height();
    }

    @Override
    public boolean matches(
            CraftingInput input,
            @NonNull Level world
    ) {
        return this.rawContents.matches(input);
    }

    @Override @NonNull
    public ItemStack assemble(CraftingInput input) {
        return this.resultStack.create();
    }

    @Override
    public boolean showNotification() {
        return this.notification;
    }

    @Override @NonNull
    public String group() {
        return this.recipeGroup;
    }

    @Override @NonNull
    public RecipeType<CraftingRecipe> getType() {
        return RegRecipes.SUPREME_CRAFTING_TYPE;
    }

    @Override @NonNull
    public CraftingBookCategory category() {
        return this.recipeCategory;
    }

    @Override @NonNull
    public RecipeBookCategory recipeBookCategory() {
        return RegRecipes.SUPREME_CRAFTING;
    }

    @Override @NonNull
    public PlacementInfo placementInfo() {
        return PlacementInfo.createFromOptionals(this.rawContents.ingredients());
    }

    @Override @NonNull
    public RecipeSerializer<SupremeCraftingRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override @NonNull
    public List<RecipeDisplay> display() {
        List<SlotDisplay> ingredientDisplays = this.rawContents.ingredients().stream()
                .map((ingredient) -> ingredient
                        .map(Ingredient::display)
                        .orElse(SlotDisplay.Empty.INSTANCE)
                )
                .toList();

        SlotDisplay resultDisplay = new SlotDisplay.ItemSlotDisplay(
                this.resultStack.item().value()
        );
        SlotDisplay craftingStationDisplay = new SlotDisplay.ItemSlotDisplay(
                RegActiveBlocks.SUPREME_WORKTABLE.asItem()
        );

        return List.of(
                new ShapedCraftingRecipeDisplay(
                        getWidth(),
                        getHeight(),
                        ingredientDisplays,
                        resultDisplay,
                        craftingStationDisplay
                )
        );
    }

    private static SupremeCraftingRecipe read(RegistryFriendlyByteBuf buf) {
        String group = buf.readUtf();
        CraftingBookCategory category = buf.readEnum(CraftingBookCategory.class);
        ShapedRecipePattern raw = ShapedRecipePattern.STREAM_CODEC.decode(buf);
        ItemStackTemplate result = ItemStackTemplate.STREAM_CODEC.decode(buf);
        boolean showNotification = buf.readBoolean();

        return new SupremeCraftingRecipe(group, category, raw, result, showNotification);
    }

    private static void write(RegistryFriendlyByteBuf buf, SupremeCraftingRecipe recipe) {
        buf.writeUtf(recipe.recipeGroup);
        buf.writeEnum(recipe.recipeCategory);
        ShapedRecipePattern.STREAM_CODEC.encode(buf, recipe.rawContents);

        ItemStackTemplate.STREAM_CODEC.encode(buf, recipe.resultStack);
        buf.writeBoolean(recipe.notification);
    }

}
