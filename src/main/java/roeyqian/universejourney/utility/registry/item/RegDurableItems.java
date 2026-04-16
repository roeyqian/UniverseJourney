/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.utility.registry.item;

// Minecraft
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;
import roeyqian.universejourney.item.CustomArmorMaterials;
import roeyqian.universejourney.item.durable.SupremeMobile;
import roeyqian.universejourney.item.durable.UniverseBoots;
import roeyqian.universejourney.item.durable.UniverseChestplate;
import roeyqian.universejourney.item.durable.UniverseConsole;
import roeyqian.universejourney.item.durable.UniverseHelmet;
import roeyqian.universejourney.item.durable.UniverseLeggings;
import roeyqian.universejourney.item.durable.UniverseOmniBlade;
import roeyqian.universejourney.item.durable.UniverseUltimaSword;

public final class RegDurableItems {

    // Supreme Handheld
    public static final Item SUPREME_MOBILE = ItemRegHelper.registerDurableItem(
            "supreme_mobile", SupremeMobile::new,
            new Item.Properties()
    );

    // Supreme Armor
    public static final Item SUPREME_HELMET = ItemRegHelper.registerDurableItem(
            "supreme_helmet", Item::new,
            new Item.Properties().humanoidArmor(CustomArmorMaterials.SUPREME_ARMOR, ArmorType.HELMET).enchantable(50)
    );
    public static final Item SUPREME_CHESTPLATE = ItemRegHelper.registerDurableItem(
            "supreme_chestplate", Item::new,
            new Item.Properties().humanoidArmor(CustomArmorMaterials.SUPREME_ARMOR, ArmorType.CHESTPLATE).enchantable(50)
    );
    public static final Item SUPREME_LEGGINGS = ItemRegHelper.registerDurableItem(
            "supreme_leggings", Item::new,
            new Item.Properties().humanoidArmor(CustomArmorMaterials.SUPREME_ARMOR, ArmorType.LEGGINGS).enchantable(50)
    );
    public static final Item SUPREME_BOOTS = ItemRegHelper.registerDurableItem(
            "supreme_boots", Item::new,
            new Item.Properties().humanoidArmor(CustomArmorMaterials.SUPREME_ARMOR, ArmorType.BOOTS).enchantable(50)
    );

    // Universe Handheld
    public static final Item UNIVERSE_ULTIMA_SWORD = ItemRegHelper.registerDurableItem(
            "universe_ultima_sword", UniverseUltimaSword::new,
            new Item.Properties()
    );
    public static final Item UNIVERSE_OMNI_BLADE = ItemRegHelper.registerDurableItem(
            "universe_omni_blade", UniverseOmniBlade::new,
            new Item.Properties()
    );
    public static final Item UNIVERSE_CONSOLE = ItemRegHelper.registerDurableItem(
            "universe_console", UniverseConsole::new,
            new Item.Properties()
    );

    // Universe Armor
    public static final Item UNIVERSE_HELMET = ItemRegHelper.registerDurableItem(
            "universe_helmet", UniverseHelmet::new,
            new Item.Properties().humanoidArmor(CustomArmorMaterials.UNIVERSE_ARMOR, ArmorType.HELMET)
    );
    public static final Item UNIVERSE_CHESTPLATE = ItemRegHelper.registerDurableItem(
            "universe_chestplate", UniverseChestplate::new,
            new Item.Properties().humanoidArmor(CustomArmorMaterials.UNIVERSE_ARMOR, ArmorType.CHESTPLATE)
    );
    public static final Item UNIVERSE_LEGGINGS = ItemRegHelper.registerDurableItem(
            "universe_leggings", UniverseLeggings::new,
            new Item.Properties().humanoidArmor(CustomArmorMaterials.UNIVERSE_ARMOR, ArmorType.LEGGINGS)
    );
    public static final Item UNIVERSE_BOOTS = ItemRegHelper.registerDurableItem(
            "universe_boots", UniverseBoots::new,
            new Item.Properties().humanoidArmor(CustomArmorMaterials.UNIVERSE_ARMOR, ArmorType.BOOTS)
    );

    public static void init() {
        UniverseJourney.LOGGER.info("[Server] Registering 'RegDurableItems'");
    }

}
