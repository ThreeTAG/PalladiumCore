package net.threetag.palladiumcore.event;

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

}
