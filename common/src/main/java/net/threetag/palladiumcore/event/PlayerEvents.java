package net.threetag.palladiumcore.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class PlayerEvents {

    public static final Event<Join> JOIN = new Event<>(Join.class, listeners -> (p) -> {
        for (Join listener : listeners) {
            listener.playerJoin(p);
        }
    });

    public static final Event<Quit> QUIT = new Event<>(Quit.class, listeners -> (p) -> {
        for (Quit listener : listeners) {
            listener.playerQuit(p);
        }
    });

    public static final Event<Tracking> START_TRACKING = new Event<>(Tracking.class, listeners -> (p, t) -> {
        for (Tracking listener : listeners) {
            listener.playerTracking(p, t);
        }
    });

    public static final Event<Tracking> STOP_TRACKING = new Event<>(Tracking.class, listeners -> (p, t) -> {
        for (Tracking listener : listeners) {
            listener.playerTracking(p, t);
        }
    });

    @FunctionalInterface
    public interface Join {

        void playerJoin(Player player);

    }

    @FunctionalInterface
    public interface Quit {

        void playerQuit(Player player);

    }

    @FunctionalInterface
    public interface Tracking {

        void playerTracking(Player tracker, Entity target);

    }

}
