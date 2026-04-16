/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.block;

// Minecraft
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

// JetBrains Specify
import org.jspecify.annotations.NonNull;

public interface CustomBlockEntityInventory extends Container {

    @Override
    default void setChanged() {}

    @Override
    default void clearContent() {
        getItems().clear();
    }

    @Override
    default int getContainerSize() {
        return getItems().size();
    }

    @Override
    default boolean isEmpty() {
        for (int i = 0; i < getContainerSize(); i++) {
            if (!getItem(i).isEmpty()) return false;
        }
        return true;
    }

    @Override
    default boolean stillValid(
            @NonNull Player player
    ) {
        return true;
    }

    @Override @NonNull
    default ItemStack getItem(
            int slot
    ) {
        return getItems().get(slot);
    }

    @Override
    default void setItem(
            int slot,
            @NonNull ItemStack stack
    ) {
        getItems().set(slot, stack);
        if (stack.getCount() > stack.getMaxStackSize()) stack.setCount(stack.getMaxStackSize());
        setChanged();
    }

    @Override @NonNull
    default ItemStack removeItem(
            int slot,
            int count
    ) {
        ItemStack result = ContainerHelper.removeItem(getItems(), slot, count);
        if (!result.isEmpty()) setChanged();
        return result;
    }

    @Override @NonNull
    default ItemStack removeItemNoUpdate(
            int slot
    ) {
        return ContainerHelper.takeItem(getItems(), slot);
    }

    NonNullList<ItemStack> getItems();

}
