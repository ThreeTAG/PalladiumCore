package net.threetag.palladiumcore.util;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.threetag.palladiumcore.event.LifecycleEvents;
import net.threetag.palladiumcore.event.PlayerEvents;
import net.threetag.palladiumcore.network.NetworkManager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Allows automatic synchronisation of entity data in all scenarios. Simply register the message, PalladiumCore will do the rest
 */
public class DataSyncUtil {

    private static final List<DataSync> DATA_SYNC = new ArrayList<>();
    private static final List<EntitySync> ENTITY_SYNC = new ArrayList<>();

    public static void registerDataSync(DataSync dataSync) {
        DATA_SYNC.add(dataSync);
    }

    public static void registerEntitySync(EntitySync entitySync) {
        ENTITY_SYNC.add(entitySync);
    }

    public static void setupEvents() {
        LifecycleEvents.DATAPACK_SYNC.register((playerList, player) -> {
            for (DataSync dataSync : DATA_SYNC) {
                if (player == null) {
                    dataSync.gatherPayloads(payload -> {
                        for (ServerPlayer pl : playerList.getPlayers()) {
                            NetworkManager.get().sendToPlayer(pl, payload);
                        }
                    });
                } else {
                    dataSync.gatherPayloads(payload -> NetworkManager.get().sendToPlayer(player, payload));
                }
            }
        });

        PlayerEvents.JOIN.register(player -> {
            if (player instanceof ServerPlayer serverPlayer) {
                for (EntitySync entitySync : ENTITY_SYNC) {
                    entitySync.gatherPayloads(serverPlayer, payload -> NetworkManager.get().sendToPlayer(serverPlayer, payload));
                }
            }
        });

        PlayerEvents.START_TRACKING.register((tracker, target) -> {
            if (tracker instanceof ServerPlayer serverPlayer) {
                for (EntitySync entitySync : ENTITY_SYNC) {
                    entitySync.gatherPayloads(target, payload -> NetworkManager.get().sendToPlayer(serverPlayer, payload));
                }
            }
        });

        PlayerEvents.RESPAWN.register((player, endConquered) -> {
            if (player instanceof ServerPlayer serverPlayer) {
                for (EntitySync entitySync : ENTITY_SYNC) {
                    entitySync.gatherPayloads(player, payload -> NetworkManager.get().sendToPlayersTrackingEntityAndSelf(serverPlayer, payload));
                }
            }
        });

        PlayerEvents.CHANGED_DIMENSION.register((player, destination) -> {
            if (player instanceof ServerPlayer serverPlayer) {
                for (EntitySync entitySync : ENTITY_SYNC) {
                    entitySync.gatherPayloads(player, payload -> NetworkManager.get().sendToPlayersTrackingEntityAndSelf(serverPlayer, payload));
                }
            }
        });
    }

    @FunctionalInterface
    public interface EntitySync {

        void gatherPayloads(Entity entity, Consumer<CustomPacketPayload> consumer);

    }

    @FunctionalInterface
    public interface DataSync {

        void gatherPayloads(Consumer<CustomPacketPayload> consumer);

    }

}
