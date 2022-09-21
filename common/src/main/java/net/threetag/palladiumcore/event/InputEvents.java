package net.threetag.palladiumcore.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.world.entity.player.Player;

public interface InputEvents {

    /**
     * @see KeyPressed#keyPressed(Minecraft, int, int, int, int)
     */
    Event<KeyPressed> KEY_PRESSED = new Event<>(KeyPressed.class, listeners -> (m, k, s, a, mo) -> {
        for (KeyPressed listener : listeners) {
            listener.keyPressed(m, k, s, a, mo);
        }
    });

    /**
     * @see MouseClickedPre#mouseClickedPre(Minecraft, int, int, int)
     */
    Event<MouseClickedPre> MOUSE_CLICKED_PRE = new Event<>(MouseClickedPre.class, listeners -> (c, b, a, m) -> Event.result(listeners, clicked -> clicked.mouseClickedPre(c, b, a, m)));

    /**
     * @see MouseClickedPost#mouseClickedPost(Minecraft, int, int, int)
     */
    Event<MouseClickedPost> MOUSE_CLICKED_POST = new Event<>(MouseClickedPost.class, listeners -> (c, b, a, m) -> {
        for (MouseClickedPost listener : listeners) {
            listener.mouseClickedPost(c, b, a, m);
        }
    });

    /**
     * @see MovementInputUpdate#movementInputUpdate(Player, Input) 
     */
    Event<MovementInputUpdate> MOVEMENT_INPUT_UPDATE = new Event<>(MovementInputUpdate.class, listeners -> (p, i) -> {
        for (MovementInputUpdate listener : listeners) {
            listener.movementInputUpdate(p, i);
        }
    });

    @FunctionalInterface
    interface KeyPressed {

        /**
         * Called when a key is pressed
         *
         * @param minecraft The current Minecraft instance
         * @param keyCode   The {@code GLFW} (platform-agnostic) key code
         * @param scanCode  The platform-specific scan code
         * @param action    The mouse button's action
         * @param mods      The bit field representing the active modifier keys
         */
        void keyPressed(Minecraft minecraft, int keyCode, int scanCode, int action, int mods);

    }

    @FunctionalInterface
    interface MouseClickedPre {

        /**
         * Fired when a mouse button is pressed/released, before being processed by vanilla.
         *
         * @param client The current Minecraft instance
         * @param button The mouse button's input code
         * @param action The mouse button's action
         * @param mods   The bit field representing the active modifier keys
         * @return A {@link EventResult} representing the result of the event, if cancelled the vanilla mechanics will be interrupted
         */
        EventResult mouseClickedPre(Minecraft client, int button, int action, int mods);

    }

    @FunctionalInterface
    interface MouseClickedPost {

        /**
         * Fired when a mouse button is pressed/released, after processing.
         *
         * @param client The current Minecraft instance
         * @param button The mouse button's input code {@see GLFW mouse constants starting with 'GLFW_MOUSE_BUTTON_'}
         * @param action The mouse button's action
         * @param mods   The bit field representing the active modifier keys
         */
        void mouseClickedPost(Minecraft client, int button, int action, int mods);

    }

    @FunctionalInterface
    interface MovementInputUpdate {

        /**
         * Fired after the player's movement inputs are updated.
         *
         * @param player The client's player whose inputs are getting updated
         * @param input  The player's movement input
         */
        void movementInputUpdate(Player player, Input input);

    }

}
