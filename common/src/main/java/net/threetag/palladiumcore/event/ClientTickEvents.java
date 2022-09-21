package net.threetag.palladiumcore.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

@Environment(EnvType.CLIENT)
public interface ClientTickEvents {

    /**
     * Called at the start of the client tick.
     */
    Event<ClientTick> CLIENT_PRE = new Event<>(ClientTick.class, listeners -> (m) -> {
        for (ClientTick listener : listeners) {
            listener.clientTick(m);
        }
    });

    /**
     * Called at the end of the client tick.
     */
    Event<ClientTick> CLIENT_POST = new Event<>(ClientTick.class, listeners -> (m) -> {
        for (ClientTick listener : listeners) {
            listener.clientTick(m);
        }
    });

    /**
     * Called at the start of a {@link ClientLevel}'s tick.
     */
    Event<ClientLevelTick> CLIENT_LEVEL_PRE = new Event<>(ClientLevelTick.class, listeners -> (m, l) -> {
        for (ClientLevelTick listener : listeners) {
            listener.clientLevelTick(m, l);
        }
    });

    /**
     * Called at the end of a {@link ClientLevel}'s tick.
     */
    Event<ClientLevelTick> CLIENT_LEVEL_POST = new Event<>(ClientLevelTick.class, listeners -> (m, l) -> {
        for (ClientLevelTick listener : listeners) {
            listener.clientLevelTick(m, l);
        }
    });

    @FunctionalInterface
    interface ClientTick {

        void clientTick(Minecraft minecraft);

    }

    @FunctionalInterface
    interface ClientLevelTick {

        void clientLevelTick(Minecraft minecraft, ClientLevel clientLevel);

    }

}
