package net.threetag.palladiumcore.event;

import net.minecraft.client.gui.screens.Screen;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicReference;

public interface ScreenEvents {

    Event<Opening> OPENING = new Event<>(Opening.class, listeners -> (s, a) -> Event.result(listeners, clicked -> clicked.screenOpening(s, a)));

    Event<InitPre> INIT_PRE = new Event<>(InitPre.class, listeners -> (s) -> Event.result(listeners, clicked -> clicked.screenInitPre(s)));

    Event<InitPost> INIT_POST = new Event<>(InitPost.class, listeners -> (s) -> {
        for (InitPost listener : listeners) {
            listener.screenInitPost(s);
        }
    });

    @FunctionalInterface
    interface Opening {

        EventResult screenOpening(@Nullable Screen currentScreen, AtomicReference<Screen> newScreen);

    }

    @FunctionalInterface
    interface InitPre {

        EventResult screenInitPre(Screen screen);

    }

    @FunctionalInterface
    interface InitPost {

        void screenInitPost(Screen screen);

    }

}
