package net.threetag.palladiumcore.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.threetag.palladiumcore.event.PlayerEvents;
import net.threetag.palladiumcore.network.MessageS2C;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Allows automatic synchronisation of entity data in all scenarios. Simply register the message, PalladiumCore will do the rest
 */
public class DataSyncUtil {

    private static final List<Function<Entity, MessageS2C>> FUNCTIONS = new ArrayList<>();

    public static void registerMessage(Function<Entity, MessageS2C> function) {
        FUNCTIONS.add(function);
    }

    public static void setupEvents() {
        PlayerEvents.JOIN.register(player -> {
            if (player instanceof ServerPlayer serverPlayer) {
                for (Function<Entity, MessageS2C> function : FUNCTIONS) {
                    function.apply(serverPlayer).send(serverPlayer);
                }
            }
        });

        PlayerEvents.START_TRACKING.register((tracker, target) -> {
            if (tracker instanceof ServerPlayer serverPlayer) {
                for (Function<Entity, MessageS2C> function : FUNCTIONS) {
                    function.apply(target).send(serverPlayer);
                }
            }
        });

        PlayerEvents.RESPAWN.register((player, endConquered) -> {
            if (player instanceof ServerPlayer serverPlayer) {
                for (Function<Entity, MessageS2C> function : FUNCTIONS) {
                    function.apply(player).sendToTrackingAndSelf(serverPlayer);
                }
            }
        });

        PlayerEvents.CHANGED_DIMENSION.register((player, destination) -> {
            if (player instanceof ServerPlayer serverPlayer) {
                for (Function<Entity, MessageS2C> function : FUNCTIONS) {
                    function.apply(player).sendToTrackingAndSelf(serverPlayer);
                }
            }
        });
    }

}
