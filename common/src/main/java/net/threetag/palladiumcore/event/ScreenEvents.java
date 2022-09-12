package net.threetag.palladiumcore.event;

import net.minecraft.client.gui.screens.Screen;

public class ScreenEvents {

    public static final Event<InitPre> INIT_PRE = new Event<>(InitPre.class, listeners -> (s) -> Event.result(listeners, clicked -> clicked.screenInitPre(s)));

    public static final Event<InitPost> INIT_POST = new Event<>(InitPost.class, listeners -> (s) -> {
        for (InitPost listener : listeners) {
            listener.screenInitPost(s);
        }
    });


    @FunctionalInterface
    public interface InitPre {

        EventResult screenInitPre(Screen screen);

    }

    @FunctionalInterface
    public interface InitPost {

        void screenInitPost(Screen screen);

    }

}
