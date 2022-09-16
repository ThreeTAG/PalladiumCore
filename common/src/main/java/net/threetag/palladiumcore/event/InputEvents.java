package net.threetag.palladiumcore.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.world.entity.player.Player;

public interface InputEvents {

    Event<KeyPressed> KEY_PRESSED = new Event<>(KeyPressed.class, listeners -> (m, k, s, a, mo) -> {
        for (KeyPressed listener : listeners) {
            listener.keyPressed(m, k, s, a, mo);
        }
    });

    Event<MouseClickedPre> MOUSE_CLICKED_PRE = new Event<>(MouseClickedPre.class, listeners -> (c, b, a, m) -> Event.result(listeners, clicked -> clicked.mouseClickedPre(c, b, a, m)));

    Event<MouseClickedPost> MOUSE_CLICKED_POST = new Event<>(MouseClickedPost.class, listeners -> (c, b, a, m) -> {
        for (MouseClickedPost listener : listeners) {
            listener.mouseClickedPost(c, b, a, m);
        }
    });

    Event<MovementInputUpdate> MOVEMENT_INPUT_UPDATE = new Event<>(MovementInputUpdate.class, listeners -> (p, i) -> {
        for (MovementInputUpdate listener : listeners) {
            listener.movementInputUpdate(p, i);
        }
    });

    @FunctionalInterface
    interface KeyPressed {

        void keyPressed(Minecraft minecraft, int keyCode, int scanCode, int action, int mods);

    }

    @FunctionalInterface
    interface MouseClickedPre {

        EventResult mouseClickedPre(Minecraft client, int button, int action, int mods);
    }

    @FunctionalInterface
    interface MouseClickedPost {

        void mouseClickedPost(Minecraft client, int button, int action, int mods);
    }

    @FunctionalInterface
    interface MovementInputUpdate {

        void movementInputUpdate(Player player, Input input);

    }

}
