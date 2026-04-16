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
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

// Minecraft
import net.minecraft.client.Options;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;
import roeyqian.universejourney.utility.registry.item.RegItemNetworks;

@Environment(EnvType.CLIENT)
public final class RegUniverseBootsFlashing {

    private static final long DOUBLE_TAP_WINDOW = 150;
    private static final long DASH_COOLDOWN = 150;

    private static final long[] lastReleaseTime = new long[4];
    private static final boolean[] wasPressed = new boolean[4];
    private static final boolean[] waitingSecondTap = new boolean[4];
    private static long lastDashTime = 0;

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.screen != null) return;

            if (!client.player.isCrouching()) {
                for (int i = 0; i < 4; i++) {
                    waitingSecondTap[i] = false;
                    wasPressed[i] = false;
                }
                return;
            }

            Options opt = client.options;
            boolean[] pressed = {
                    opt.keyUp.isDown(),    // 0=W
                    opt.keyLeft.isDown(),  // 1=A
                    opt.keyDown.isDown(),  // 2=S
                    opt.keyRight.isDown()  // 3=D
            };

            long now = System.currentTimeMillis();

            for (int i = 0; i < 4; i++) {
                if (pressed[i] && !wasPressed[i]) {
                    if (waitingSecondTap[i]
                            && (now - lastReleaseTime[i]) < DOUBLE_TAP_WINDOW
                            && (now - lastDashTime) > DASH_COOLDOWN) {
                        ClientPlayNetworking.send(new RegItemNetworks.UniverseBootsDashPayload(i));
                        lastDashTime = now;
                        waitingSecondTap[i] = false;
                    }
                } else if (!pressed[i] && wasPressed[i]) {
                    lastReleaseTime[i] = now;
                    waitingSecondTap[i] = true;
                }

                if (waitingSecondTap[i] && (now - lastReleaseTime[i]) > DOUBLE_TAP_WINDOW) {
                    waitingSecondTap[i] = false;
                }

                wasPressed[i] = pressed[i];
            }
        });

        UniverseJourney.LOGGER.info("[Client] Registering 'RegUniverseBootsFlashing'");
    }
}