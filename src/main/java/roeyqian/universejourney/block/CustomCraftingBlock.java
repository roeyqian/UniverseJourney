/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.block;

// Minecraft
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;

// Java Standard
import java.util.List;
import java.util.Optional;

public interface CustomCraftingBlock {

    List<Slot> getSlots();
    int getResultId();
    int getInventoryStart();
    int getInventoryEnd();
    int getHotbarStart();
    int getHotbarEnd();
    int getInputStart();
    int getInputEnd();
    boolean noInsertItem(ItemStack stack, int start, int end, boolean fromPlayer);

    default ItemStack quickMove(
            Player player,
            int slot
    ) {
        Slot currentSlot = getSlots().get(slot);
        if (!currentSlot.hasItem()) return ItemStack.EMPTY;

        ItemStack originalStack = currentSlot.getItem();
        ItemStack movedStack = originalStack.copy();

        if (slot == getResultId()) {
            return handleResultSlot(player, currentSlot, originalStack, movedStack);
        }
        if (slot >= getInventoryStart() && slot < getHotbarEnd()) {
            return handleInventorySlot(player, currentSlot, originalStack, movedStack, slot);
        }
        if (noInsertItem(movedStack, getInventoryStart(), getHotbarEnd(), false)) {
            return ItemStack.EMPTY;
        }

        updateSlotAfterMove(currentSlot, originalStack, movedStack, player, slot);
        return movedStack;
    }

    default void updateResult(
            AbstractContainerMenu handler,
            ServerLevel world,
            Player player,
            CraftingContainer inputInv,
            ResultContainer resultInv,
            RecipeHolder<CraftingRecipe> recipe
    ) {
        CraftingInput craftingRecipeInput = inputInv.asCraftInput();
        ServerPlayer serverPlayerEntity = (ServerPlayer) player;
        ItemStack itemStack = ItemStack.EMPTY;

        Optional<RecipeHolder<CraftingRecipe>> optional = world
                .getServer()
                .getRecipeManager()
                .getRecipeFor(RecipeType.CRAFTING, craftingRecipeInput, world, recipe);

        if (optional.isPresent()) {
            RecipeHolder<CraftingRecipe> recipeEntry = optional.get();
            CraftingRecipe craftingRecipe = recipeEntry.value();

            if (resultInv.setRecipeUsed(serverPlayerEntity, recipeEntry)) {
                ItemStack itemStack2 = craftingRecipe.assemble(craftingRecipeInput);
                if (itemStack2.isItemEnabled(world.enabledFeatures())) itemStack = itemStack2;
            }
        }

        if (itemStack.isEmpty()) itemStack = findCustomRecipeResult(craftingRecipeInput, world);

        resultInv.setItem(0, itemStack);
        handler.setRemoteSlot(0, itemStack);

        serverPlayerEntity.connection.send(
                new ClientboundContainerSetSlotPacket(handler.containerId, handler.incrementStateId(), 0, itemStack)
        );
    }

    ItemStack findCustomRecipeResult(CraftingInput input, ServerLevel world);

    private ItemStack handleResultSlot(
            Player player,
            Slot slot,
            ItemStack original,
            ItemStack moved
    ) {
        original.getItem().onCraftedBy(original, player);
        if (noInsertItem(moved, getInventoryStart(), getHotbarEnd(), true)) return ItemStack.EMPTY;

        slot.onQuickCraft(moved, original);
        updateSlotAfterMove(slot, original, moved, player, getResultId());
        return moved;
    }

    private ItemStack handleInventorySlot(
            Player player,
            Slot slot,
            ItemStack original,
            ItemStack moved,
            int srcSlot
    ) {
        if (!noInsertItem(moved, getInputStart(), getInputEnd(), false)) {
            updateSlotAfterMove(slot, original, moved, player, srcSlot);
            return moved;
        }

        boolean isInMainInventory = srcSlot < getInventoryEnd();
        int fallbackStart = isInMainInventory ? getHotbarStart() : getInventoryStart();
        int fallbackEnd = isInMainInventory ? getHotbarEnd() : getInventoryEnd();

        if (!noInsertItem(moved, fallbackStart, fallbackEnd, false)) {
            updateSlotAfterMove(slot, original, moved, player, srcSlot);
            return moved;
        }

        return ItemStack.EMPTY;
    }

    private void updateSlotAfterMove(
            Slot slot,
            ItemStack original,
            ItemStack moved,
            Player player,
            int slotIndex
    ) {
        if (original.isEmpty()) slot.setByPlayer(ItemStack.EMPTY);
        else slot.setChanged();

        if (original.getCount() == moved.getCount()) return;
        slot.onTake(player, original);

        if (slotIndex == getResultId()) player.drop(original, false);
        original.setCount(0);
    }

}
