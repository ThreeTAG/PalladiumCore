package net.threetag.palladiumcore.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicReference;

public interface ScreenEvents {

    /**
     * @see Opening#screenOpening(Screen, AtomicReference)
     */
    Event<Opening> OPENING = new Event<>(Opening.class, listeners -> (s, a) -> Event.result(listeners, clicked -> clicked.screenOpening(s, a)));

    /**
     * @see InitPre#screenInitPre(Screen)
     */
    Event<InitPre> INIT_PRE = new Event<>(InitPre.class, listeners -> (s) -> Event.result(listeners, clicked -> clicked.screenInitPre(s)));

    /**
     * @see InitPost#screenInitPost(Screen)
     */
    Event<InitPost> INIT_POST = new Event<>(InitPost.class, listeners -> (s) -> {
        for (InitPost listener : listeners) {
            listener.screenInitPost(s);
        }
    });

    @FunctionalInterface
    interface Opening {

        /**
         * Called before a {@link Screen} is opened. Allows for preventing the opening of the {@link Screen} or changing it entirely
         *
         * @param currentScreen The {@link Screen} which was open before the new screen was opened
         * @param newScreen     Atomic reference for the new {@link Screen} that is set to be opened, can be changed to manipulate
         * @return An {@link EventResult} to potentially cancel the opening of the {@link Screen}
         */
        EventResult screenOpening(@Nullable Screen currentScreen, AtomicReference<Screen> newScreen);

    }

    @FunctionalInterface
    interface InitPre {

        /**
         * Called during the start of a {@link Screen} being initialized
         * @see Screen#init(Minecraft, int, int)
         *
         * @param screen {@link Screen} which was opened
         * @return An {@link EventResult} that may cancel the initialization
         */
        EventResult screenInitPre(Screen screen);

    }

    @FunctionalInterface
    interface InitPost {

        /**
         * Called during the end of a {@link Screen} being initialized
         * @see Screen#init(Minecraft, int, int)
         *
         * @param screen {@link Screen} which was opened
         */
        void screenInitPost(Screen screen);

    }

}
