package net.threetag.palladiumcore.event;

import net.minecraft.server.MinecraftServer;

public interface LifecycleEvents {

    Event<Runnable> SETUP = new Event<>(Runnable.class, listeners -> () -> {
        for (Runnable listener : listeners) {
            listener.run();
        }
    });

    Event<Runnable> CLIENT_SETUP = new Event<>(Runnable.class, listeners -> () -> {
        for (Runnable listener : listeners) {
            listener.run();
        }
    });

    Event<Server> SERVER_ABOUT_TO_START = new Event<>(Server.class, listeners -> (s) -> {
        for (Server listener : listeners) {
            listener.server(s);
        }
    });

    Event<Server> SERVER_STARTING = new Event<>(Server.class, listeners -> (s) -> {
        for (Server listener : listeners) {
            listener.server(s);
        }
    });

    Event<Server> SERVER_STARTED = new Event<>(Server.class, listeners -> (s) -> {
        for (Server listener : listeners) {
            listener.server(s);
        }
    });

    Event<Server> SERVER_STOPPING = new Event<>(Server.class, listeners -> (s) -> {
        for (Server listener : listeners) {
            listener.server(s);
        }
    });

    Event<Server> SERVER_STOPPED = new Event<>(Server.class, listeners -> (s) -> {
        for (Server listener : listeners) {
            listener.server(s);
        }
    });

    @FunctionalInterface
    interface Server {

        void server(MinecraftServer server);

    }

}
