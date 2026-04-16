/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.utility.registry.item;

// Fabric
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

// Minecraft
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

// JetBrains Specify
import org.jspecify.annotations.NonNull;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;
import roeyqian.universejourney.item.RemoteAccessManager;

public final class RegItemNetworks {

    public static void init() {
        ItemNetworkRegHelper.registerDurableItemModeNetworking();
        ItemNetworkRegHelper.registerUniverseBootsNetworking();

        RemoteAccessManager.REMOTE_ACCESS_TICKET = RemoteAccessManager.register();
        PayloadTypeRegistry.serverboundPlay().register(
                RegItemNetworks.UniverseConsoleBoundBlockPayload.ID,
                RegItemNetworks.UniverseConsoleBoundBlockPayload.CODEC
        );
        ServerPlayNetworking.registerGlobalReceiver(
                RegItemNetworks.UniverseConsoleBoundBlockPayload.ID,
                (payload, context) -> context.server().execute(
                        () -> ItemNetworkRegHelper.registerUniverseConsoleBoundBlockAction(payload, context)
                )
        );

        UniverseJourney.LOGGER.info("[Server] Registering 'RegItemNetworks'");
    }

    public record DurableItemModePayload()
            implements CustomPacketPayload {

        public static final Type<DurableItemModePayload> UNIVERSE_MODE_CHANGE =
                new Type<>(Identifier.fromNamespaceAndPath(UniverseJourney.MOD_ID, "universe_mode_change"));
        public static final StreamCodec<RegistryFriendlyByteBuf, DurableItemModePayload> CODEC =
                StreamCodec.unit(new DurableItemModePayload());
        @Override @NonNull
        public Type<? extends CustomPacketPayload> type() {
            return UNIVERSE_MODE_CHANGE;
        }

    }

    public record UniverseBootsDashPayload
            (int direction) implements CustomPacketPayload {

        public static final Type<UniverseBootsDashPayload> ID =
                new Type<>(Identifier.fromNamespaceAndPath(UniverseJourney.MOD_ID, "dash"));
        public static final StreamCodec<RegistryFriendlyByteBuf, UniverseBootsDashPayload> CODEC =
                StreamCodec.composite(
                        ByteBufCodecs.VAR_INT,
                        UniverseBootsDashPayload::direction,
                        UniverseBootsDashPayload::new
                );
        @Override @NonNull
        public Type<? extends CustomPacketPayload> type() {
            return ID;
        }

    }

    public record UniverseConsoleBoundBlockPayload
            (Action act, BlockPos pos, ResourceKey<Level> dimension) implements CustomPacketPayload {

        public enum Action {
            OPEN, REMOVE
        }

        public static final Type<UniverseConsoleBoundBlockPayload> ID = new Type<>(
                Identifier.fromNamespaceAndPath(UniverseJourney.MOD_ID, "bound_block_action")
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, UniverseConsoleBoundBlockPayload> CODEC =
                StreamCodec.composite(
                        StreamCodec.ofMember((value, buf) -> buf.writeEnum(value), buf -> buf.readEnum(Action.class)),
                        UniverseConsoleBoundBlockPayload::act,

                        BlockPos.STREAM_CODEC,
                        UniverseConsoleBoundBlockPayload::pos,

                        ResourceKey.streamCodec(Registries.DIMENSION),
                        UniverseConsoleBoundBlockPayload::dimension,
                        UniverseConsoleBoundBlockPayload::new
                );
        @Override @NonNull
        public Type<? extends CustomPacketPayload> type() {
            return ID;
        }

    }

}
