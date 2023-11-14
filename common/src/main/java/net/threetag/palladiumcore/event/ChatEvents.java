package net.threetag.palladiumcore.event;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public interface ChatEvents {

    /**
     * @see ServerSubmitted#chatMessageSubmitted(ServerPlayer, String, Component)
     */
    Event<ServerSubmitted> SERVER_SUBMITTED = new Event<>(ServerSubmitted.class, listeners -> (p, r, m) -> Event.result(listeners, submitted -> submitted.chatMessageSubmitted(p, r, m)));

    @FunctionalInterface
    interface ServerSubmitted {

        /**
         * Fired when a chat message was received from the client. Cancelling it will stop it from appearing in the chat.
         *
         * @param player The player sending the message
         * @param rawMessage Original raw chat message
         * @param message The chat component that will be sent to all clients
         * @return Event result, that determines if the message will be sent
         */
        EventResult chatMessageSubmitted(ServerPlayer player, String rawMessage, Component message);

    }

}
