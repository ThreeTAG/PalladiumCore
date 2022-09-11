package net.threetag.palladiumcore.event;

public class EventResult {

    private static final EventResult CANCEL = new EventResult(true, true);
    private static final EventResult CANCEL_AND_CONTINUE = new EventResult(true, false);
    private static final EventResult STOP_LISTENERS = new EventResult(false, true);
    private static final EventResult PASS = new EventResult(false, false);

    private final boolean cancelEvent;
    private final boolean stopListeners;

    private EventResult(boolean cancelEvent, boolean stopListeners) {
        this.cancelEvent = cancelEvent;
        this.stopListeners = stopListeners;
    }

    public boolean cancelsEvent() {
        return this.cancelEvent;
    }

    public boolean stopsListeners() {
        return this.stopListeners;
    }

    public static EventResult cancel() {
        return CANCEL;
    }

    public static EventResult cancelAndContinue() {
        return CANCEL_AND_CONTINUE;
    }

    public static EventResult stopListeners() {
        return STOP_LISTENERS;
    }

    public static EventResult pass() {
        return PASS;
    }

}
