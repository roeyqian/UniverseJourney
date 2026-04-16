/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.item;

// Minecraft
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;

// Google Guava
import com.google.common.collect.Maps;

// Java Standard
import java.util.Map;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;

public interface CustomArmorMaterials extends ArmorMaterials {

    int supreme = 256;
    int universe = 1024;

    ArmorMaterial SUPREME_ARMOR = new ArmorMaterial(
            supreme,
            makeDefense(supreme, supreme, supreme, supreme, supreme),
            supreme,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            supreme,
            (supreme * 0.1F),
            TagKey.create(
                    Registries.ITEM,
                    Identifier.fromNamespaceAndPath(UniverseJourney.MOD_ID, "supreme_core")
            ),
            ResourceKey.create(
                    EquipmentAssets.ROOT_ID,
                    Identifier.fromNamespaceAndPath(UniverseJourney.MOD_ID, "supreme")
            )
    );
    ArmorMaterial UNIVERSE_ARMOR = new ArmorMaterial(
            universe,
            makeDefense(universe, universe, universe, universe, universe),
            universe,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            universe,
            (universe * 0.1F),
            TagKey.create(
                    Registries.ITEM,
                    Identifier.fromNamespaceAndPath(UniverseJourney.MOD_ID, "universe_star")
            ),
            ResourceKey.create(
                    EquipmentAssets.ROOT_ID,
                    Identifier.fromNamespaceAndPath(UniverseJourney.MOD_ID, "universe")
            )
    );

    private static Map<ArmorType, Integer> makeDefense(
            int feet,
            int legs,
            int chest,
            int head,
            int body
    ) {
        return Maps.newEnumMap(
                Map.of(
                        ArmorType.BOOTS, feet,
                        ArmorType.LEGGINGS, legs,
                        ArmorType.CHESTPLATE, chest,
                        ArmorType.HELMET, head,
                        ArmorType.BODY, body
                )
        );
    }

}
