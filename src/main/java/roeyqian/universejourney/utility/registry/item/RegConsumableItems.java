/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.utility.registry.item;

// Minecraft
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

// Universe Journey
import net.minecraft.world.item.SpawnEggItem;
import roeyqian.universejourney.UniverseJourney;
import roeyqian.universejourney.item.CustomItemSettings;
import roeyqian.universejourney.item.consumable.StrangeLingeringPotion;
import roeyqian.universejourney.item.consumable.StrangePotion;
import roeyqian.universejourney.item.consumable.StrangeSplashPotion;
import roeyqian.universejourney.item.consumable.UniverseGuardianSpawnEgg;
import roeyqian.universejourney.item.consumable.UniverseStar;
import roeyqian.universejourney.item.consumable.UniverseStick;
import roeyqian.universejourney.utility.registry.block.RegInsertBlocks;
import roeyqian.universejourney.utility.registry.entity.RegLiveEntities;

public final class RegConsumableItems {

    // Supreme Group: Material
    public static final Item SUPREME_METAL = ItemRegHelper.registerConsumableItem(
            "supreme_metal",
            Item::new,
            new Item.Properties().rarity(Rarity.RARE)
    );
    public static final Item SUPREME_CRYSTAL = ItemRegHelper.registerConsumableItem(
            "supreme_crystal",
            Item::new,
            new Item.Properties().rarity(Rarity.RARE)
    );
    public static final Item SEED_OF_ALL_THINGS = ItemRegHelper.registerConsumableItem(
            "seed_of_all_things", settings -> new BlockItem(RegInsertBlocks.CROP_OF_ALL_THINGS, settings),
            new Item.Properties()
    );
    public static final Item SUPREME_BANQUET = ItemRegHelper.registerConsumableItem(
            "supreme_banquet",
            Item::new,
            new Item.Properties().rarity(Rarity.RARE)
    );
    public static final Item SUPREME_CORE = ItemRegHelper.registerConsumableItem(
            "supreme_core", Item::new,
            new Item.Properties().rarity(Rarity.RARE)
    );
    public static final Item STRANGE_MATTER = ItemRegHelper.registerConsumableItem(
            "strange_matter",
            Item::new,
            new Item.Properties().rarity(Rarity.EPIC)
    );

    // Supreme Group: Potion
    public static final Item STRANGE_POTION = ItemRegHelper.registerDurableItem(
            "strange_potion",
            StrangePotion::new,
            new Item.Properties().rarity(Rarity.EPIC)
    );
    public static final Item STRANGE_SPLASH_POTION = ItemRegHelper.registerDurableItem(
            "strange_splash_potion",
            StrangeSplashPotion::new,
            new Item.Properties().rarity(Rarity.EPIC)
    );
    public static final Item STRANGE_LINGERING_POTION = ItemRegHelper.registerDurableItem(
            "strange_lingering_potion",
            StrangeLingeringPotion::new,
            new Item.Properties().rarity(Rarity.EPIC)
    );

    // Supreme Group: Food
    public static final Item FOOD_OF_ALL_THINGS = ItemRegHelper.registerConsumableItem(
            "food_of_all_things",
            Item::new,
            new Item.Properties().food(new FoodProperties(10, 100.0F, false))
    );

    // Supreme Group: Spawn Egg
    public static final Item ENDER_DRAGON_SPAWN_EGG = ItemRegHelper.registerConsumableItem(
            "ender_dragon_spawn_egg", SpawnEggItem::new,
            CustomItemSettings.applySupremeDefaults(new Item.Properties())
                    .spawnEgg(EntityType.ENDER_DRAGON)
                    .component(
                            DataComponents.LORE,
                            CustomItemSettings.supremeLore("ender_dragon_spawn_egg", 1)
                    )
    );
    public static final Item WITHER_SPAWN_EGG = ItemRegHelper.registerConsumableItem(
            "wither_spawn_egg", SpawnEggItem::new,
            CustomItemSettings.applySupremeDefaults(new Item.Properties())
                    .spawnEgg(EntityType.WITHER)
                    .component(
                            DataComponents.LORE,
                            CustomItemSettings.supremeLore("wither_spawn_egg", 1)
                    )
    );
    public static final Item SKULK_BEHEMOTH_SPAWN_EGG = ItemRegHelper.registerConsumableItem(
            "skulk_behemoth_spawn_egg", SpawnEggItem::new,
            CustomItemSettings.applySupremeDefaults(new Item.Properties())
                    .spawnEgg(RegLiveEntities.SKULK_BEHEMOTH)
                    .component(
                            DataComponents.LORE,
                            CustomItemSettings.supremeLore("wither_spawn_egg", 1)
                    )
    );
    public static final Item THE_UNNAMEABLE_EGG = ItemRegHelper.registerConsumableItem(
            "the_unnameable_egg", SpawnEggItem::new,
            CustomItemSettings.applySupremeDefaults(new Item.Properties())
                    .spawnEgg(RegLiveEntities.THE_UNNAMEABLE_THING)
                    .component(
                            DataComponents.LORE,
                            CustomItemSettings.supremeLore("wither_spawn_egg", 1)
                    )
    );
    public static final Item BELL_RINGER_SPAWN_EGG = ItemRegHelper.registerConsumableItem(
            "bell_ringer_spawn_egg", SpawnEggItem::new,
            CustomItemSettings.applySupremeDefaults(new Item.Properties())
                    .spawnEgg(RegLiveEntities.BELL_RINGER)
                    .component(
                            DataComponents.LORE,
                            CustomItemSettings.supremeLore("wither_spawn_egg", 1)
                    )
    );
    public static final Item BELL_SOUL_SPAWN_EGG = ItemRegHelper.registerConsumableItem(
            "bell_soul_spawn_egg", SpawnEggItem::new,
            CustomItemSettings.applySupremeDefaults(new Item.Properties())
                    .spawnEgg(RegLiveEntities.BELL_SOUL)
                    .component(
                            DataComponents.LORE,
                            CustomItemSettings.supremeLore("wither_spawn_egg", 1)
                    )
    );
    public static final Item PALE_LORD_SPAWN_EGG = ItemRegHelper.registerConsumableItem(
            "pale_lord_spawn_egg", SpawnEggItem::new,
            CustomItemSettings.applySupremeDefaults(new Item.Properties())
                    .spawnEgg(RegLiveEntities.PALE_LORD_BODY)
                    .component(
                            DataComponents.LORE,
                            CustomItemSettings.supremeLore("wither_spawn_egg", 1)
                    )
    );
    public static final Item OBSIDIAN_GOLEM_SPAWN_EGG = ItemRegHelper.registerConsumableItem(
            "obsidian_golem_spawn_egg", SpawnEggItem::new,
            CustomItemSettings.applySupremeDefaults(new Item.Properties())
                    .spawnEgg(RegLiveEntities.OBSIDIAN_GOLEM)
    );

    // Universe Group: Spawn Egg
    public static final Item UNIVERSE_GUARDIAN_SPAWN_EGG = ItemRegHelper.registerConsumableItem(
            "universe_guardian_spawn_egg", UniverseGuardianSpawnEgg::new,
            new Item.Properties()
    );

    // Universe Group: Material
    public static final Item UNIVERSE_PRIMARY_FRAGMENT = ItemRegHelper.registerConsumableItem(
            "universe_primary_fragment", Item::new,
            new Item.Properties().rarity(Rarity.EPIC)
    );
    public static final Item UNIVERSE_GEMRED = ItemRegHelper.registerConsumableItem(
            "universe_gemred", Item::new,
            new Item.Properties().rarity(Rarity.EPIC)
    );
    public static final Item UNIVERSE_GEMBLUE = ItemRegHelper.registerConsumableItem(
            "universe_gemblue", Item::new,
            new Item.Properties().rarity(Rarity.EPIC)
    );
    public static final Item UNIVERSE_GEMYELLOW = ItemRegHelper.registerConsumableItem(
            "universe_gemyellow", Item::new,
            new Item.Properties().rarity(Rarity.EPIC)
    );
    public static final Item UNIVERSE_GEMGREEN = ItemRegHelper.registerConsumableItem(
            "universe_gemgreen",
            Item::new,
            new Item.Properties().rarity(Rarity.EPIC)
    );
    public static final Item UNIVERSE_GEMBLACK = ItemRegHelper.registerConsumableItem(
            "universe_gemblack",
            Item::new,
            new Item.Properties().rarity(Rarity.EPIC)
    );
    public static final Item UNIVERSE_GEMWHITE = ItemRegHelper.registerConsumableItem(
            "universe_gemwhite",
            Item::new,
            new Item.Properties().rarity(Rarity.EPIC)
    );
    public static final Item UNIVERSE_STAR = ItemRegHelper.registerConsumableItem(
            "universe_star", UniverseStar::new,
            new Item.Properties()
    );
    public static final Item UNIVERSE_STICK = ItemRegHelper.registerConsumableItem(
            "universe_stick", UniverseStick::new,
            new Item.Properties()
    );

    public static void init() {
        int universeFuelTime = Integer.MAX_VALUE / 5;

        ItemRegHelper.registerFuel(universeFuelTime, UNIVERSE_GEMRED);
        ItemRegHelper.registerFuel(universeFuelTime, UNIVERSE_GEMBLUE);
        ItemRegHelper.registerFuel(universeFuelTime, UNIVERSE_GEMYELLOW);
        ItemRegHelper.registerFuel(universeFuelTime, UNIVERSE_GEMGREEN);
        ItemRegHelper.registerFuel(universeFuelTime, UNIVERSE_GEMBLACK);
        ItemRegHelper.registerFuel(universeFuelTime, UNIVERSE_GEMWHITE);

        UniverseJourney.LOGGER.info("[Server] Registering 'RegConsumableItems'");
    }

}
