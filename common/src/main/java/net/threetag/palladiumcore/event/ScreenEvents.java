package net.threetag.palladiumcore.event;

import net.minecraft.client.gui.screens.Screen;

public interface ScreenEvents {

    Event<InitPre> INIT_PRE = new Event<>(InitPre.class, listeners -> (s) -> Event.result(listeners, clicked -> clicked.screenInitPre(s)));

    Event<InitPost> INIT_POST = new Event<>(InitPost.class, listeners -> (s) -> {
        for (InitPost listener : listeners) {
            listener.screenInitPost(s);
        }
    });


    @FunctionalInterface
    interface InitPre {

        EventResult screenInitPre(Screen screen);

    }

    @FunctionalInterface
    interface InitPost {

        void screenInitPost(Screen screen);

    }

}
