/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.utility.registry.input;

// Fabric
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

// Mojang
import com.mojang.blaze3d.platform.InputConstants;

// Minecraft
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

// Lightweight Java Game Library
import org.lwjgl.glfw.GLFW;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;
import roeyqian.universejourney.item.durable.UniverseOmniBlade;
import roeyqian.universejourney.item.durable.UniverseUltimaSword;
import roeyqian.universejourney.item.durable.SupremeMobile;
import roeyqian.universejourney.item.durable.UniverseConsole;
import roeyqian.universejourney.utility.registry.item.RegItemNetworks;

@Environment(EnvType.CLIENT)
public final class RegKeyBindings {

    public static final KeyMapping.Category UNIVERSE_CATEGORY =
            KeyMapping.Category.register(Identifier.fromNamespaceAndPath(UniverseJourney.MOD_ID, "general"));

    public static final KeyMapping universeModeKey = KeyMappingHelper.registerKeyMapping(
            new KeyMapping(
                    "key.universejourney.universe_mode",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_U,
                    UNIVERSE_CATEGORY
            )
    );

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (RegKeyBindings.universeModeKey.consumeClick()) {
                if (client.player == null) return;

                Item item = client.player.getMainHandItem().getItem();
                if (item instanceof SupremeMobile ||
                        item instanceof UniverseUltimaSword ||
                        item instanceof UniverseOmniBlade ||
                        item instanceof UniverseConsole) {
                    ClientPlayNetworking.send(new RegItemNetworks.DurableItemModePayload());
                }
            }
        });

        UniverseJourney.LOGGER.info("[Client] Registering 'RegKeyBindings'");
    }
}