/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.utility.registry.entity;

// Minecraft
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;
import roeyqian.universejourney.entity.live.BellRinger;
import roeyqian.universejourney.entity.live.BellSoul;
import roeyqian.universejourney.entity.live.ObsidianGolem;
import roeyqian.universejourney.entity.live.PaleLordBody;
import roeyqian.universejourney.entity.live.PaleLordClone;
import roeyqian.universejourney.entity.live.SkulkBehemoth;
import roeyqian.universejourney.entity.live.TheUnnameableThing;
import roeyqian.universejourney.entity.live.UniverseGuardian;

public final class RegLiveEntities {

    public static final ResourceKey<EntityType<?>> SKULK_BEHEMOTH_KEY =
            EntityRegHelper.entityKey("skulk_behemoth");
    public static final ResourceKey<EntityType<?>> BELL_RINGER_KEY =
            EntityRegHelper.entityKey("bell_ringer");
    public static final ResourceKey<EntityType<?>> UNIVERSE_GUARDIAN_KEY =
            EntityRegHelper.entityKey("universe_guardian");
    public static final ResourceKey<EntityType<?>> THE_UNNAMEABLE_THING_KEY =
            EntityRegHelper.entityKey("the_unnameable_thing");
    public static final ResourceKey<EntityType<?>> PALE_LORD_BODY_KEY =
            EntityRegHelper.entityKey("pale_lord_body");
    public static final ResourceKey<EntityType<?>> PALE_LORD_CLONE_KEY =
            EntityRegHelper.entityKey("pale_lord_clone");
    public static final ResourceKey<EntityType<?>> BELL_SOUL_KEY =
            EntityRegHelper.entityKey("bell_soul");
    public static final ResourceKey<EntityType<?>> OBSIDIAN_GOLEM_KEY =
            EntityRegHelper.entityKey("obsidian_golem");

    public static final EntityType<SkulkBehemoth> SKULK_BEHEMOTH = EntityRegHelper.register(
            SKULK_BEHEMOTH_KEY,
            SkulkBehemoth::new,
            MobCategory.MONSTER,
            8.0F,
            10.0F
    );
    public static final EntityType<BellRinger> BELL_RINGER = EntityRegHelper.register(
            BELL_RINGER_KEY,
            BellRinger::new,
            MobCategory.MONSTER,
            0.6F,
            1.95F
    );
    public static final EntityType<UniverseGuardian> UNIVERSE_GUARDIAN = EntityRegHelper.register(
            UNIVERSE_GUARDIAN_KEY,
            UniverseGuardian::new,
            MobCategory.CREATURE,
            0.6F,
            1.4F
    );
    public static final EntityType<TheUnnameableThing> THE_UNNAMEABLE_THING = EntityRegHelper.register(
            THE_UNNAMEABLE_THING_KEY,
            TheUnnameableThing::new,
            MobCategory.MONSTER,
            1.2F,
            1.2F
    );
    public static final EntityType<PaleLordBody> PALE_LORD_BODY = EntityRegHelper.register(
            PALE_LORD_BODY_KEY,
            PaleLordBody::new,
            MobCategory.MONSTER,
            0.6F,
            1.95F
    );
    public static final EntityType<PaleLordClone> PALE_LORD_CLONE = EntityRegHelper.register(
            PALE_LORD_CLONE_KEY,
            PaleLordClone::new,
            MobCategory.MONSTER,
            0.6F,
            1.95F
    );
    public static final EntityType<BellSoul> BELL_SOUL = EntityRegHelper.register(
            BELL_SOUL_KEY,
            BellSoul::new,
            MobCategory.MONSTER,
            0.4F,
            0.8F
    );
    public static final EntityType<ObsidianGolem> OBSIDIAN_GOLEM = EntityRegHelper.register(
            OBSIDIAN_GOLEM_KEY,
            ObsidianGolem::new,
            MobCategory.MONSTER,
            1.4F,
            2.7F
    );

    public static void init() {
        EntityRegHelper.registerAttributes(
                SKULK_BEHEMOTH,
                SkulkBehemoth.createAttributes()
        );
        EntityRegHelper.registerAttributes(
                BELL_RINGER,
                BellRinger.createAttributes()
        );
        EntityRegHelper.registerAttributes(
                UNIVERSE_GUARDIAN,
                UniverseGuardian.createAttributes()
        );
        EntityRegHelper.registerAttributes(
                THE_UNNAMEABLE_THING,
                TheUnnameableThing.createAttributes()
        );
        EntityRegHelper.registerAttributes(
                PALE_LORD_BODY,
                PaleLordBody.createAttributes()
        );
        EntityRegHelper.registerAttributes(
                PALE_LORD_CLONE,
                PaleLordClone.createAttributes()
        );
        EntityRegHelper.registerAttributes(
                BELL_SOUL,
                BellSoul.createAttributes()
        );
        EntityRegHelper.registerAttributes(
                OBSIDIAN_GOLEM,
                ObsidianGolem.createAttributes()
        );

        UniverseJourney.LOGGER.info("[Server] Registering 'RegLiveEntities'");
    }

}
