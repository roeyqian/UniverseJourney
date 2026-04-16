/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.render.layer;

// Fabric
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

// Minecraft
import net.minecraft.resources.Identifier;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;

@Environment(EnvType.CLIENT)
public class UniverseEquipmentRenderLayers {

    public static boolean useRainbowGlint = false;

    public static final Identifier RAINBOW_TEXTURE_ID = Identifier.fromNamespaceAndPath(
            UniverseJourney.MOD_ID,
            "textures/misc/universe_rainbow_glint.png"
    );

}