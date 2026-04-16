/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.utility.registry.gen;

// Mojang
import com.mojang.serialization.Codec;

// Minecraft
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;

// Java Standard
import java.util.function.UnaryOperator;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;
import roeyqian.universejourney.item.durable.UniverseConsole;

public final class RegComponentTypes {

    public static final DataComponentType<Integer> UNIVERSE_ULTIMA_SWORD_MODE =
            register(
                    "universe_ultima_sword_mode",
                    builder -> builder
                            .persistent(Codec.INT)
                            .networkSynchronized(ByteBufCodecs.VAR_INT)
            );

    public static final DataComponentType<Integer> UNIVERSE_OMNI_BLADE_MODE =
            register(
                    "universe_omni_blade_mode",
                    builder -> builder
                            .persistent(Codec.INT)
                            .networkSynchronized(ByteBufCodecs.VAR_INT)
            );

    public static final DataComponentType<Integer> UNIVERSE_CONSOLE_MODE =
            register(
                    "universe_console_mode",
                    builder -> builder
                            .persistent(Codec.INT)
                            .networkSynchronized(ByteBufCodecs.VAR_INT)
            );
    public static final DataComponentType<UniverseConsole.BoundBlockList> UNIVERSE_CONSOLE_BOUND_LIST =
            register(
                    "universe_console_bound_list",
                    builder -> builder
                            .persistent(UniverseConsole.BoundBlockList.CODEC)
                            .networkSynchronized(UniverseConsole.BoundBlockList.PACKET_CODEC)
            );

    public static final DataComponentType<Integer> SUPREME_MOBILE_MODE =
            register(
                    "supreme_mobile_mode",
                    builder -> builder
                            .persistent(Codec.INT)
                            .networkSynchronized(ByteBufCodecs.VAR_INT)
            );
    public static final DataComponentType<String> SUPREME_MOBILE_BLOCK_ID =
            register(
                    "supreme_mobile_block_id",
                    builder -> builder
                            .persistent(Codec.STRING)
                            .networkSynchronized(ByteBufCodecs.STRING_UTF8)
            );

    private static <T> DataComponentType<T> register(
            String id,
            UnaryOperator<DataComponentType.Builder<T>> builderOperator
    ) {
        return GenRegHelper.registerDataComponentType(id, builderOperator);
    }

    public static void init() {
        UniverseJourney.LOGGER.info("[Server] Registering 'RegComponentTypes'");
    }

}
