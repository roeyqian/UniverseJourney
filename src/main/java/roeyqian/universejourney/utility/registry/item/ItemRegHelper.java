/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.utility.registry.item;

// Fabric
import net.fabricmc.fabric.api.registry.FuelValueEvents;

// Minecraft
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

// Java Standard
import java.util.function.Function;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;

public interface ItemRegHelper {

    static void registerFuel(
            int time,
            Item fuel
    ) {
        FuelValueEvents.BUILD.register((builder, _) -> builder.add(fuel, time));
    }

    static Item registerConsumableItem(
            String name,
            Function<Item.Properties, Item> factory,
            Item.Properties settings
    ) {
        return registerItem(name, factory, settings.stacksTo(64));
    }

    static Item registerDurableItem(
            String name,
            Function<Item.Properties, Item> factory,
            Item.Properties settings
    ) {
        return registerItem(name, factory, settings.stacksTo(1));
    }

    private static Item registerItem(
            String name,
            Function<Item.Properties, Item> factory,
            Item.Properties settings
    ) {
        ResourceKey<Item> key = ResourceKey.create(
                Registries.ITEM,
                Identifier.fromNamespaceAndPath(UniverseJourney.MOD_ID, name)
        );
        Item item = factory.apply(settings.setId(key));
        if (item instanceof BlockItem blockItem) blockItem.registerBlocks(Item.BY_BLOCK, item);
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

}
