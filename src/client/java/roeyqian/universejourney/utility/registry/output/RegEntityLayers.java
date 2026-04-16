/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.utility.registry.output;

// Fabric
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;

// Minecraft
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.Identifier;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;
import roeyqian.universejourney.model.BellRingerModel;
import roeyqian.universejourney.model.BellSoulModel;
import roeyqian.universejourney.model.ObsidianGolemModel;
import roeyqian.universejourney.model.PaleLordModel;
import roeyqian.universejourney.model.SkulkBehemothModel;
import roeyqian.universejourney.model.TheUnnameableThingModel;
import roeyqian.universejourney.model.UniverseGuardianModel;
import roeyqian.universejourney.render.entity.BellRingerRenderer;
import roeyqian.universejourney.render.entity.BellSoulRenderer;
import roeyqian.universejourney.render.entity.PaleLordBodyRenderer;
import roeyqian.universejourney.render.entity.PaleLordCloneRenderer;
import roeyqian.universejourney.render.entity.SkulkBehemothRenderer;
import roeyqian.universejourney.render.entity.TheUnnameableThingRenderer;
import roeyqian.universejourney.render.entity.UniverseGuardianRenderer;
import roeyqian.universejourney.render.entity.ObsidianGolemRenderer;
import roeyqian.universejourney.utility.registry.entity.RegLiveEntities;

public final class RegEntityLayers {

    public static final ModelLayerLocation SKULK_BEHEMOTH = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath(UniverseJourney.MOD_ID, "skulk_behemoth"), "main"
    );
    public static final ModelLayerLocation BELL_RINGER = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath(UniverseJourney.MOD_ID, "bell_ringer"), "main"
    );
    public static final ModelLayerLocation UNIVERSE_GUARDIAN = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath(UniverseJourney.MOD_ID, "universe_guardian"), "main"
    );
    public static final ModelLayerLocation THE_UNNAMEABLE_THING = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath(UniverseJourney.MOD_ID, "the_unnameable_thing"), "main"
    );
    public static final ModelLayerLocation PALE_LORD_BODY = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath(UniverseJourney.MOD_ID, "pale_lord_body"), "main"
    );
    public static final ModelLayerLocation PALE_LORD_CLONE = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath(UniverseJourney.MOD_ID, "pale_lord_clone"), "main"
    );
    public static final ModelLayerLocation BELL_SOUL = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath(UniverseJourney.MOD_ID, "bell_soul"), "main"
    );
    public static final ModelLayerLocation OBSIDIAN_GOLEM = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath(UniverseJourney.MOD_ID, "obsidian_golem"), "main"
    );

    public static void init() {
        ModelLayerRegistry.registerModelLayer(SKULK_BEHEMOTH, SkulkBehemothModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(BELL_RINGER, BellRingerModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(UNIVERSE_GUARDIAN, UniverseGuardianModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(THE_UNNAMEABLE_THING, TheUnnameableThingModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(PALE_LORD_BODY, PaleLordModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(PALE_LORD_CLONE, PaleLordModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(BELL_SOUL, BellSoulModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(OBSIDIAN_GOLEM, ObsidianGolemModel::createBodyLayer);

        EntityRenderers.register(RegLiveEntities.SKULK_BEHEMOTH, SkulkBehemothRenderer::new);
        EntityRenderers.register(RegLiveEntities.BELL_RINGER, BellRingerRenderer::new);
        EntityRenderers.register(RegLiveEntities.UNIVERSE_GUARDIAN, UniverseGuardianRenderer::new);
        EntityRenderers.register(RegLiveEntities.THE_UNNAMEABLE_THING, TheUnnameableThingRenderer::new);
        EntityRenderers.register(RegLiveEntities.PALE_LORD_BODY, PaleLordBodyRenderer::new);
        EntityRenderers.register(RegLiveEntities.PALE_LORD_CLONE, PaleLordCloneRenderer::new);
        EntityRenderers.register(RegLiveEntities.BELL_SOUL, BellSoulRenderer::new);
        EntityRenderers.register(RegLiveEntities.OBSIDIAN_GOLEM, ObsidianGolemRenderer::new);

        UniverseJourney.LOGGER.info("[Client] Registering 'RegEntityLayers'");
    }
}