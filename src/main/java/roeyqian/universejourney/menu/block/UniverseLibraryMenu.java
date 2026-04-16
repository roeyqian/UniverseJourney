/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.menu.block;

// Minecraft
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

// JetBrains Specify
import org.jspecify.annotations.NonNull;

// Universe Journey
import roeyqian.universejourney.block.active.entity.UniverseLibraryEntity;
import roeyqian.universejourney.utility.registry.menu.RegBlockMenus;

public class UniverseLibraryMenu extends AbstractContainerMenu {

    public final DataSlot scrollOffset = DataSlot.standalone();

    private final Container sourceInventory;
    private final Container displayInventory = new SimpleContainer(54);
    private boolean isRefreshing = false;

    public UniverseLibraryMenu(
            int syncId,
            Inventory playerInventory
    ) {
        this(syncId, playerInventory, new SimpleContainer(252));
    }

    public UniverseLibraryMenu(
            int syncId,
            Inventory playerInventory,
            Container inventory
    ) {
        super(RegBlockMenus.UNIVERSE_LIBRARY_HANDLER, syncId);
        this.sourceInventory = inventory;
        this.addDataSlot(this.scrollOffset);

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(
                        new DisplaySlot(
                                displayInventory,
                                col + row * 9,
                                8 + col * 18,
                                18 + row * 18
                        )
                );
            }
        }
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(
                        new Slot(
                                playerInventory,
                                col + row * 9 + 9,
                                8 + col * 18,
                                140 + row * 18
                        )
                );
            }
        }

        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 198));
        }

        if (!playerInventory.player.level().isClientSide()) {
            this.scrollOffset.set(0);
            this.refreshDisplay(0);
        }
    }

    public int getInventorySize() {
        return this.sourceInventory.getContainerSize();
    }

    @Override
    public boolean stillValid(
            @NonNull Player player
    ) {
        return true;
    }

    @Override @NonNull
    public ItemStack quickMoveStack(
            @NonNull Player player,
            int index
    ) {
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack original = slot.getItem();
            ItemStack copy = original.copy();

            if (index < 54) {
                if (!this.moveItemStackTo(original, 54, 90, true)) return ItemStack.EMPTY;
            } else {
                if (!this.moveItemStackTo(original, 0, 54, false)) return ItemStack.EMPTY;
            }

            if (original.isEmpty()) slot.setByPlayer(ItemStack.EMPTY);
            else slot.setChanged();
            return copy;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void removed(
            @NonNull Player player
    ) {
        super.removed(player);

        if (!player.level().isClientSide()
                && this.sourceInventory instanceof UniverseLibraryEntity libraryBe) {
            libraryBe.setOpened(false);
            Level world = libraryBe.getLevel();
            if (world == null) return;

            world.blockEvent(
                    libraryBe.getBlockPos(),
                    libraryBe.getBlockState().getBlock(),
                    1,
                    0
            );
            world.playSound(
                    null,
                    libraryBe.getBlockPos(),
                    SoundEvents.ENDER_CHEST_CLOSE,
                    SoundSource.BLOCKS,
                    0.5f,
                    world.getRandom().nextFloat() * 0.1f + 0.9f
            );
        }
    }

    @Override
    public boolean clickMenuButton(
            Player player,
            int id
    ) {
        if (player.level().isClientSide()) return true;

        int maxOffset = Math.max(0, (int) Math.ceil(getInventorySize() / 9.0) - 6);
        if (id >= 0 && id <= maxOffset) {
            if (!this.getCarried().isEmpty()) {
                player.drop(this.getCarried(), false);
                this.setCarried(ItemStack.EMPTY);
            }

            this.scrollOffset.set(id);
            refreshDisplay(id);
            return true;
        }
        return false;
    }

    private void refreshDisplay(
            int rowOffset
    ) {
        this.isRefreshing = true;
        for (int i = 0; i < 54; i++) {
            int realIdx = i + (rowOffset * 9);
            ItemStack sourceStack =
                    (realIdx < sourceInventory.getContainerSize())
                            ? sourceInventory.getItem(realIdx)
                            : ItemStack.EMPTY;
            this.displayInventory.setItem(i, sourceStack.copy());
        }
        this.broadcastChanges();
        this.isRefreshing = false;
    }

    private class DisplaySlot extends Slot {

        public DisplaySlot(
                Container inventory,
                int index,
                int x,
                int y
        ) {
            super(inventory, index, x, y);
        }

        @Override @NonNull
        public ItemStack getItem() {
            ItemStack sourceStack = UniverseLibraryMenu.this.sourceInventory.getItem(getRealIndex());
            return sourceStack.isEmpty() ? super.getItem() : sourceStack;
        }

        @Override
        public void setByPlayer(
                @NonNull ItemStack stack
        ) {
            super.setByPlayer(stack);
            if (!isRefreshing) UniverseLibraryMenu.this.sourceInventory.setItem(getRealIndex(), stack.copy());
        }

        @Override @NonNull
        public ItemStack remove(
                int amount
        ) {
            ItemStack stack = UniverseLibraryMenu.this.sourceInventory.removeItem(getRealIndex(), amount);

            if (stack.isEmpty()) stack = super.remove(amount);
            else super.setByPlayer(UniverseLibraryMenu.this.sourceInventory.getItem(getRealIndex()));
            return stack;
        }

        private int getRealIndex() {
            return this.getContainerSlot() + (UniverseLibraryMenu.this.scrollOffset.get() * 9);
        }

    }

}
