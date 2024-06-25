package net.threetag.palladiumcore.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.threetag.palladiumcore.event.LifecycleEvents;
import net.threetag.palladiumcore.event.PlayerEvents;
import net.threetag.palladiumcore.network.MessageS2C;

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
                    dataSync.gatherMessages(msg -> {
                        for (ServerPlayer pl : playerList.getPlayers()) {
                            msg.send(pl);
                        }
                    });
                } else {
                    dataSync.gatherMessages(msg -> msg.send(player));
                }
            }
        });

        PlayerEvents.JOIN.register(player -> {
            if (player instanceof ServerPlayer serverPlayer) {
                for (EntitySync entitySync : ENTITY_SYNC) {
                    entitySync.gatherMessages(serverPlayer, msg -> msg.send(serverPlayer));
                }
            }
        });

        PlayerEvents.START_TRACKING.register((tracker, target) -> {
            if (tracker instanceof ServerPlayer serverPlayer) {
                for (EntitySync entitySync : ENTITY_SYNC) {
                    entitySync.gatherMessages(target, msg -> msg.send(serverPlayer));
                }
            }
        });

        PlayerEvents.RESPAWN.register((player, endConquered) -> {
            if (player instanceof ServerPlayer serverPlayer) {
                for (EntitySync entitySync : ENTITY_SYNC) {
                    entitySync.gatherMessages(player, msg -> msg.sendToTrackingAndSelf(serverPlayer));
                }
            }
        });

        PlayerEvents.CHANGED_DIMENSION.register((player, destination) -> {
            if (player instanceof ServerPlayer serverPlayer) {
                for (EntitySync entitySync : ENTITY_SYNC) {
                    entitySync.gatherMessages(player, msg -> msg.sendToTrackingAndSelf(serverPlayer));
                }
            }
        });
    }

    @FunctionalInterface
    public interface EntitySync {

        void gatherMessages(Entity entity, Consumer<MessageS2C> consumer);

    }

    @FunctionalInterface
    public interface DataSync {

        void gatherMessages(Consumer<MessageS2C> consumer);

    }

}
