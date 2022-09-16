package net.threetag.palladiumcore.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public interface PlayerEvents {

    Event<Join> JOIN = new Event<>(Join.class, listeners -> (p) -> {
        for (Join listener : listeners) {
            listener.playerJoin(p);
        }
    });

    Event<Quit> QUIT = new Event<>(Quit.class, listeners -> (p) -> {
        for (Quit listener : listeners) {
            listener.playerQuit(p);
        }
    });

    Event<Tracking> START_TRACKING = new Event<>(Tracking.class, listeners -> (p, t) -> {
        for (Tracking listener : listeners) {
            listener.playerTracking(p, t);
        }
    });

    Event<Tracking> STOP_TRACKING = new Event<>(Tracking.class, listeners -> (p, t) -> {
        for (Tracking listener : listeners) {
            listener.playerTracking(p, t);
        }
    });

    @FunctionalInterface
    interface Join {

        void playerJoin(Player player);

    }

    @FunctionalInterface
    interface Quit {

        void playerQuit(Player player);

    }

    @FunctionalInterface
    interface Tracking {

        void playerTracking(Player tracker, Entity target);

    }

}
