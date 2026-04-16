/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.utility.registry.block;

// Fabric
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

// Minecraft
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;
import roeyqian.universejourney.block.active.entity.SupremeFurnaceEntity;
import roeyqian.universejourney.block.active.entity.UniverseLibraryEntity;
import roeyqian.universejourney.block.active.entity.UniverseRefineryEntity;
import roeyqian.universejourney.block.active.entity.UniverseVoidPoolEntity;

public final class RegActiveBlockEntities {

    public static final BlockEntityType<SupremeFurnaceEntity> SUPREME_FURNACE_ENTITY = register(
            "supreme_furnace_entity",
            SupremeFurnaceEntity::new,
            RegActiveBlocks.SUPREME_FURNACE
    );
    public static final BlockEntityType<UniverseRefineryEntity> UNIVERSE_REFINERY_ENTITY = register(
            "universe_refinery_entity",
            UniverseRefineryEntity::new,
            RegActiveBlocks.UNIVERSE_REFINERY
    );
    public static final BlockEntityType<UniverseVoidPoolEntity> UNIVERSE_VOID_POOL_ENTITY = register(
            "universe_void_pool_entity",
            UniverseVoidPoolEntity::new,
            RegActiveBlocks.UNIVERSE_VOID_POOL
    );
    public static final BlockEntityType<UniverseLibraryEntity> UNIVERSE_LIBRARY_ENTITY = register(
            "universe_library_entity",
            UniverseLibraryEntity::new,
            RegActiveBlocks.UNIVERSE_LIBRARY
    );

    private static <T extends BlockEntity> BlockEntityType<T> register(
            String path,
            FabricBlockEntityTypeBuilder.Factory<T> factory,
            Block block
    ) {
        return Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                Identifier.fromNamespaceAndPath(UniverseJourney.MOD_ID, path),
                FabricBlockEntityTypeBuilder.create(factory, block).build()
        );
    }

    public static void init() {
        UniverseJourney.LOGGER.info("[Server] Registering 'RegActiveBlockEntities'");
    }

}
