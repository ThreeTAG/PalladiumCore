package net.threetag.palladiumcore.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.threetag.palladiumcore.event.PlayerEvents;
import net.threetag.palladiumcore.network.MessageS2C;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Allows automatic synchronisation of entity data in all scenarios. Simply register the message, PalladiumCore will do the rest
 */
public class DataSyncUtil {

    private static final List<EntitySync> ENTITY_SYNC = new ArrayList<>();

    public static void registerEntitySync(EntitySync entitySync) {
        ENTITY_SYNC.add(entitySync);
    }

    public static void setupEvents() {
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

}
