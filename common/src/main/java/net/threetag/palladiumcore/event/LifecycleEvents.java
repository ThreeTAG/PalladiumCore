package net.threetag.palladiumcore.event;

public class LifecycleEvents {

    public static final Event<Runnable> SETUP = new Event<>(Runnable.class, listeners -> () -> {
        for (Runnable listener : listeners) {
            listener.run();
        }
    });

    public static final Event<Runnable> CLIENT_SETUP = new Event<>(Runnable.class, listeners -> () -> {
        for (Runnable listener : listeners) {
            listener.run();
        }
    });

}
