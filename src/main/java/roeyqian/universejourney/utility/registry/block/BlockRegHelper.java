/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.utility.registry.block;

// Minecraft
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

// Java Standard
import java.util.Objects;
import java.util.function.Function;
import java.util.function.UnaryOperator;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;

public interface BlockRegHelper {

    static Block registerBase(
            String name,
            String type,
            Function<BlockBehaviour.Properties, Block> factory,
            BlockBehaviour.Properties properties
    ) {
        if (Objects.equals(type, "supreme")) {
            return register(
                    name, factory, properties.strength(10.0F, 2400.0F),
                    setting -> setting.rarity(Rarity.RARE)
            );
        } else {
            return register(
                    name, factory, properties.strength(0.15F, 3600000.0F),
                    setting -> setting.rarity(Rarity.EPIC)
            );
        }
    }

    static Block registerWood(
            String name,
            String type,
            Function<BlockBehaviour.Properties, Block> factory,
            BlockBehaviour.Properties properties
    ) {
        if (Objects.equals(type, "supreme")) {
            return register(
                    name, factory, properties
                            .strength(2.0F)
                            .sound(SoundType.WOOD)
                            .ignitedByLava(),
                    setting -> setting.rarity(Rarity.RARE)
            );
        } else {
            return register(
                    name, factory, properties
                            .strength(2.0F)
                            .sound(SoundType.WOOD)
                            .ignitedByLava(),
                    setting -> setting.rarity(Rarity.EPIC)
            );
        }
    }

    static Block registerGrass(
            String name,
            String type,
            Function<BlockBehaviour.Properties, Block> factory,
            BlockBehaviour.Properties properties
    ) {
        if (Objects.equals(type, "supreme")) {
            return register(
                    name, factory, properties
                            .randomTicks()
                            .sound(SoundType.GRASS),
                    setting -> setting.rarity(Rarity.RARE)
            );
        } else {
            return register(
                    name, factory, properties
                            .randomTicks()
                            .sound(SoundType.GRASS),
                    setting -> setting.rarity(Rarity.EPIC)
            );
        }
    }

    static Block registerGravel(
            String name,
            String type,
            Function<BlockBehaviour.Properties, Block> factory,
            BlockBehaviour.Properties properties
    ) {
        if (Objects.equals(type, "supreme")) {
            return register(
                    name, factory, properties
                            .randomTicks()
                            .sound(SoundType.GRAVEL),
                    setting -> setting.rarity(Rarity.RARE)
            );
        } else {
            return register(
                    name, factory, properties
                            .randomTicks()
                            .sound(SoundType.GRAVEL),
                    setting -> setting.rarity(Rarity.EPIC)
            );
        }
    }

    static Block registerSapling(
            String name,
            String type,
            Function<BlockBehaviour.Properties, Block> factory,
            BlockBehaviour.Properties properties
    ) {
        if (Objects.equals(type, "supreme")) {
            return register(
                    name, factory, properties
                            .noCollision()
                            .randomTicks()
                            .instabreak()
                            .sound(SoundType.GRASS)
                            .pushReaction(PushReaction.DESTROY),
                    setting -> setting.rarity(Rarity.RARE)
            );
        } else {
            return register(
                    name, factory, properties
                            .noCollision()
                            .randomTicks()
                            .instabreak()
                            .sound(SoundType.GRASS)
                            .pushReaction(PushReaction.DESTROY),
                    setting -> setting.rarity(Rarity.EPIC)
            );
        }
    }

    static Block registerLeaves(
            String name,
            String type,
            Function<BlockBehaviour.Properties, Block> factory,
            BlockBehaviour.Properties properties
    ) {
        if (Objects.equals(type, "supreme")) {
            return register(
                    name, factory, properties
                            .strength(0.2F)
                            .randomTicks()
                            .sound(SoundType.GRASS)
                            .noOcclusion()
                            .isValidSpawn(Blocks::ocelotOrParrot)
                            .isSuffocating(Blocks::never)
                            .isViewBlocking(Blocks::never)
                            .ignitedByLava()
                            .pushReaction(PushReaction.DESTROY)
                            .isRedstoneConductor(Blocks::never),
                    setting -> setting.rarity(Rarity.RARE)
            );
        } else {
            return register(
                    name, factory, properties
                            .strength(0.2F)
                            .randomTicks()
                            .sound(SoundType.GRASS)
                            .noOcclusion()
                            .isValidSpawn(Blocks::ocelotOrParrot)
                            .isSuffocating(Blocks::never)
                            .isViewBlocking(Blocks::never)
                            .ignitedByLava()
                            .pushReaction(PushReaction.DESTROY)
                            .isRedstoneConductor(Blocks::never),
                    setting -> setting.rarity(Rarity.EPIC)
            );
        }
    }

    static Block registerPortal(
            String name,
            Function<BlockBehaviour.Properties, Block> factory
    ) {
        return register(
                name,
                factory,
                BlockBehaviour.Properties.of()
                        .noCollision()
                        .noLootTable()
                        .strength(-1.0F, 1.0F)
                        .sound(SoundType.GLASS),
                setting -> setting.rarity(Rarity.COMMON)
        );
    }

    private static Block register(
            String name,
            Function<BlockBehaviour.Properties, Block> factory,
            BlockBehaviour.Properties blockSettings,
            UnaryOperator<Item.Properties> itemModifier
    ) {
        Identifier id = Identifier.fromNamespaceAndPath(UniverseJourney.MOD_ID, name);
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, id);
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id);

        Block block = Blocks.register(blockKey, factory, blockSettings);
        Item.Properties itemProperties = itemModifier.apply(new Item.Properties().setId(itemKey));

        BlockItem blockItem = new BlockItem(block, itemProperties);
        blockItem.registerBlocks(Item.BY_BLOCK, blockItem);
        Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);

        return block;
    }

}
