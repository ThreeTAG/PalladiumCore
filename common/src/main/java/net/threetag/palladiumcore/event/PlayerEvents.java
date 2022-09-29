package net.threetag.palladiumcore.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public interface PlayerEvents {

    /**
     * @see Join#playerJoin(Player)
     */
    Event<Join> JOIN = new Event<>(Join.class, listeners -> (p) -> {
        for (Join listener : listeners) {
            listener.playerJoin(p);
        }
    });

    /**
     * @see Quit#playerQuit(Player)
     */
    Event<Quit> QUIT = new Event<>(Quit.class, listeners -> (p) -> {
        for (Quit listener : listeners) {
            listener.playerQuit(p);
        }
    });

    /**
     * Client version of the {@link PlayerEvents#JOIN} event
     * @see Join#playerJoin(Player)
     */
    Event<Join> CLIENT_JOIN = new Event<>(Join.class, listeners -> (p) -> {
        for (Join listener : listeners) {
            listener.playerJoin(p);
        }
    });

    /**
     * Client version of the {@link PlayerEvents#QUIT} event
     * @see Quit#playerQuit(Player)
     */
    Event<Quit> CLIENT_QUIT = new Event<>(Quit.class, listeners -> (p) -> {
        for (Quit listener : listeners) {
            listener.playerQuit(p);
        }
    });

    /**
     * Fired when an Entity is started to be "tracked" by this player, usually when an entity enters a player's view distance.
     */
    Event<Tracking> START_TRACKING = new Event<>(Tracking.class, listeners -> (p, t) -> {
        for (Tracking listener : listeners) {
            listener.playerTracking(p, t);
        }
    });

    /**
     * Fired when an Entity is stopped to be "tracked" by this player, usually the client was sent a packet to destroy the entity at this point
     */
    Event<Tracking> STOP_TRACKING = new Event<>(Tracking.class, listeners -> (p, t) -> {
        for (Tracking listener : listeners) {
            listener.playerTracking(p, t);
        }
    });

    /**
     * @see AnvilUpdate#anvilUpdate(Player, ItemStack, ItemStack, String, AtomicInteger, AtomicInteger, AtomicReference)
     */
    Event<AnvilUpdate> ANVIL_UPDATE = new Event<>(AnvilUpdate.class, listeners -> (p, l, r, n, c, m, o) -> Event.result(listeners, anvilUpdate -> anvilUpdate.anvilUpdate(p, l, r, n, c, m, o)));

    @FunctionalInterface
    interface Join {

        /**
         * Fired when the client player logs in to the server. The player should be initialized.
         *
         * @param player The player that is joining the game
         */
        void playerJoin(Player player);

    }

    @FunctionalInterface
    interface Quit {

        /**
         * Fired when the client player logs out. This event may also fire when a new integrated server is being created.
         *
         * @param player The player that is quitting the game
         */
        void playerQuit(Player player);

    }

    @FunctionalInterface
    interface Tracking {

        /**
         * @param tracker Player that is tracking the given entity
         * @param trackedEntity Entity being tracked
         */
        void playerTracking(Player tracker, Entity trackedEntity);

    }

    @FunctionalInterface
    interface AnvilUpdate {

        /**
         * @param player The player using the anvil
         * @param left   Item in the left slot
         * @param right  Item in the right slot
         * @param name   Name in the input field sent by the client, may be null if the user wishes to clear the name from the item
         * @param cost   Experience cost for this operation
         * @param materialCost Material cost for this operation
         * @param output Output of this operation
         */
        EventResult anvilUpdate(Player player, ItemStack left, ItemStack right, @Nullable String name, AtomicInteger cost, AtomicInteger materialCost, AtomicReference<ItemStack> output);

    }

}
