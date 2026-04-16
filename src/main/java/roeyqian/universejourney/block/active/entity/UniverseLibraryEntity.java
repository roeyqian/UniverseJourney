/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.block.active.entity;

// Minecraft
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestLidController;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

// JetBrains Specify
import org.jspecify.annotations.NonNull;

// Universe Journey
import roeyqian.universejourney.block.CustomBlockEntityInventory;
import roeyqian.universejourney.menu.block.UniverseLibraryMenu;
import roeyqian.universejourney.utility.registry.block.RegActiveBlockEntities;

public class UniverseLibraryEntity extends BlockEntity
        implements MenuProvider, CustomBlockEntityInventory {

    private final NonNullList<ItemStack> inventory = NonNullList.withSize(252, ItemStack.EMPTY);
    private final ChestLidController lidAnimator = new ChestLidController();
    private boolean opened = false;

    public UniverseLibraryEntity(
            BlockPos pos,
            BlockState state
    ) {
        super(RegActiveBlockEntities.UNIVERSE_LIBRARY_ENTITY, pos, state);
    }

    public boolean isOpened() {
        return this.opened;
    }

    public void setOpened(
            boolean opened
    ) {
        this.opened = opened;
        this.setChanged();
    }

    public static void tick(
            UniverseLibraryEntity libraryBe
    ) {
        libraryBe.lidAnimator.tickLid();
    }

    public float getAnimationProgress(
            float tickDelta
    ) {
        return this.lidAnimator.getOpenness(tickDelta);
    }

    @Override
    public boolean triggerEvent(
            int type,
            int data
    ) {
        if (type == 1) {
            this.lidAnimator.shouldBeOpen(data > 0);
            return true;
        }
        return super.triggerEvent(type, data);
    }

    @Override
    public int getContainerSize() {
        return 252;
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override @NonNull
    public Component getDisplayName() {
        return Component.translatable("item.universejourney.universe_library");
    }

    @Override
    public AbstractContainerMenu createMenu(
            int syncId,
            @NonNull Inventory playerInventory,
            @NonNull Player player
    ) {
        return new UniverseLibraryMenu(syncId, playerInventory, this);
    }

    @Override
    protected void loadAdditional(
            @NonNull ValueInput view
    ) {
        super.loadAdditional(view);
        ContainerHelper.loadAllItems(view, this.inventory);
    }

    @Override
    protected void saveAdditional(
            @NonNull ValueOutput view
    ) {
        super.saveAdditional(view);
        ContainerHelper.saveAllItems(view, this.inventory);
    }

    @Override
    protected void applyImplicitComponents(
            @NonNull DataComponentGetter components
    ) {
        super.applyImplicitComponents(components);

        ItemContainerContents container = components.get(DataComponents.CONTAINER);
        if (container != null) {
            this.inventory.clear();
            container.copyInto(this.inventory);
        }
    }

    @Override
    protected void collectImplicitComponents(
            DataComponentMap.@NonNull Builder builder
    ) {
        super.collectImplicitComponents(builder);

        if (!this.inventory.isEmpty()) {
            builder.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(this.inventory));
        } else {
            builder.set(
                    DataComponents.CONTAINER,
                    ItemContainerContents.fromItems(NonNullList.withSize(252, ItemStack.EMPTY))
            );
        }
    }

}
