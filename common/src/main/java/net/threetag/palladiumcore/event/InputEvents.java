package net.threetag.palladiumcore.event;

import net.minecraft.client.Minecraft;

public class InputEvents {

    public static final Event<KeyPressed> KEY_PRESSED = new Event<>(KeyPressed.class, listeners -> (m, k, s, a, mo) -> {
        for (KeyPressed listener : listeners) {
            listener.keyPressed(m, k, s, a, mo);
        }
    });

    public static final Event<MouseClickedPre> MOUSE_CLICKED_PRE = new Event<>(MouseClickedPre.class, listeners -> (c, b, a, m) -> Event.result(listeners, clicked -> clicked.mouseClickedPre(c, b, a, m)));

    public static final Event<MouseClickedPost> MOUSE_CLICKED_POST = new Event<>(MouseClickedPost.class, listeners -> (c, b, a, m) -> {
        for (MouseClickedPost listener : listeners) {
            listener.mouseClickedPost(c, b, a, m);
        }
    });

    @FunctionalInterface
    public interface KeyPressed {

        void keyPressed(Minecraft minecraft, int keyCode, int scanCode, int action, int mods);

    }

    @FunctionalInterface
    public interface MouseClickedPre {

        EventResult mouseClickedPre(Minecraft client, int button, int action, int mods);
    }

    @FunctionalInterface
    public interface MouseClickedPost {

        void mouseClickedPost(Minecraft client, int button, int action, int mods);
    }

}
