/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.utility.registry.block;

// Minecraft
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;
import roeyqian.universejourney.block.insert.CropOfAllThings;
import roeyqian.universejourney.block.insert.EverWaterFarmland;
import roeyqian.universejourney.block.insert.EverWaterGrassBlock;
import roeyqian.universejourney.block.insert.GoldenLeavesBlock;
import roeyqian.universejourney.block.insert.HarvestContinentPortal;
import roeyqian.universejourney.block.insert.OreContinentPortal;
import roeyqian.universejourney.block.insert.UniverseBlock;
import roeyqian.universejourney.gen.tree.SaplingGenerators;

public final class RegInsertBlocks {

    private static final String supreme = "supreme";
    private static final String universe = "universe";
    private static final ResourceKey<Block> EVER_WATER_SOIL_KEY = ResourceKey.create(
            Registries.BLOCK,
            Identifier.fromNamespaceAndPath(UniverseJourney.MOD_ID, "ever_water_soil")
    );

    public static final Block EVER_WATER_GRASS_BLOCK = BlockRegHelper.registerGrass(
            "ever_water_grass_block", supreme,
            properties -> new EverWaterGrassBlock(properties, EVER_WATER_SOIL_KEY),
            BlockBehaviour.Properties.of()
    );
    public static final Block CROP_OF_ALL_THINGS = BlockRegHelper.registerGrass(
            "crop_of_all_things", supreme, CropOfAllThings::new,
            BlockBehaviour.Properties.of()
    );
    public static final Block SUPREME_FODDER_BLOCK = BlockRegHelper.registerGrass(
            "supreme_fodder_block", supreme, Block::new,
            BlockBehaviour.Properties.of()
    );
    public static final Block SUPREME_GEM_BLOCK = BlockRegHelper.registerGrass(
            "supreme_gem_block", supreme, Block::new,
            BlockBehaviour.Properties.of()
    );
    public static final Block EVER_WATER_FARMLAND = BlockRegHelper.registerGravel(
            "ever_water_farmland", supreme, EverWaterFarmland::new,
            BlockBehaviour.Properties.of()
    );
    public static final Block EVER_WATER_SOIL = BlockRegHelper.registerGravel(
            "ever_water_soil", supreme, Block::new,
            BlockBehaviour.Properties.of()
    );
    public static final Block GOLDEN_SAPLING = BlockRegHelper.registerSapling(
            "golden_sapling", supreme, setting -> new SaplingBlock(SaplingGenerators.GOLDEN, setting),
            BlockBehaviour.Properties.of()
    );
    public static final Block GOLDEN_LEAVES = BlockRegHelper.registerLeaves(
            "golden_leaves", supreme, GoldenLeavesBlock::new,
            BlockBehaviour.Properties.of()
    );
    public static final Block GOLDEN_PLANKS = BlockRegHelper.registerWood(
            "golden_planks", supreme, Block::new,
            BlockBehaviour.Properties.of()
    );
    public static final Block STRIPPED_GOLDEN_WOOD = BlockRegHelper.registerWood(
            "stripped_golden_wood", supreme, RotatedPillarBlock::new,
            BlockBehaviour.Properties.of()
    );
    public static final Block GOLDEN_WOOD = BlockRegHelper.registerWood(
            "golden_wood", supreme, RotatedPillarBlock::new,
            BlockBehaviour.Properties.of()
    );
    public static final Block STRIPPED_GOLDEN_LOG = BlockRegHelper.registerWood(
            "stripped_golden_log", supreme, RotatedPillarBlock::new,
            BlockBehaviour.Properties.of()
    );
    public static final Block GOLDEN_LOG = BlockRegHelper.registerWood(
            "golden_log", supreme, RotatedPillarBlock::new,
            BlockBehaviour.Properties.of()
    );

    public static final Block UNIVERSE_PRIMARY_BLOCK = BlockRegHelper.registerBase(
            "universe_primary_block", universe, Block::new,
            BlockBehaviour.Properties.of()
    );
    public static final Block UNIVERSE_BLOCK = BlockRegHelper.registerBase(
            "universe_block", universe, UniverseBlock::new,
            BlockBehaviour.Properties.of()
    );

    public static final Block ORE_CONTINENT_PORTAL = BlockRegHelper.registerPortal(
            "ore_continent_portal", OreContinentPortal::new
    );
    public static final Block HARVEST_CONTINENT_PORTAL = BlockRegHelper.registerPortal(
            "harvest_continent_portal", HarvestContinentPortal::new
    );

    public static void init() {
        OreContinentPortal.Igniter.register();
        OreContinentPortal.registerTickEvent();

        HarvestContinentPortal.Igniter.register();
        HarvestContinentPortal.registerTickEvent();

        UniverseJourney.LOGGER.info("[Server] Registering 'RegInsertBlocks'");
    }

}
