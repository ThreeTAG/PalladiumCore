package net.threetag.palladiumcore.event;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.jetbrains.annotations.Nullable;

public interface LifecycleEvents {

    /**
     * Called once during startup.
     * Invoked during FMLCommonSetupEvent on Forge and client/server entrypoint on Fabric
     */
    Event<Runnable> SETUP = new Event<>(Runnable.class, listeners -> () -> {
        for (Runnable listener : listeners) {
            listener.run();
        }
    });

    /**
     * Called once during client startup.
     * Invoked during FMLClientSetupEvent on Forge and client entrypoint on Fabric
     */
    Event<Runnable> CLIENT_SETUP = new Event<>(Runnable.class, listeners -> () -> {
        for (Runnable listener : listeners) {
            listener.run();
        }
    });

    /**
     * Called before the server begins loading anything
     */
    Event<Server> SERVER_ABOUT_TO_START = new Event<>(Server.class, listeners -> (s) -> {
        for (Server listener : listeners) {
            listener.server(s);
        }
    });

    /**
     * Called after {@link LifecycleEvents#SERVER_ABOUT_TO_START} and before {@link LifecycleEvents#SERVER_STARTED}.
     */
    Event<Server> SERVER_STARTING = new Event<>(Server.class, listeners -> (s) -> {
        for (Server listener : listeners) {
            listener.server(s);
        }
    });

    /**
     * Called after {@link LifecycleEvents#SERVER_STARTING} when the server is available and ready to play.
     */
    Event<Server> SERVER_STARTED = new Event<>(Server.class, listeners -> (s) -> {
        for (Server listener : listeners) {
            listener.server(s);
        }
    });

    /**
     * Called when the server begins shutting down, before {@link LifecycleEvents#SERVER_STOPPED}.
     */
    Event<Server> SERVER_STOPPING = new Event<>(Server.class, listeners -> (s) -> {
        for (Server listener : listeners) {
            listener.server(s);
        }
    });

    /**
     * Called after {@link LifecycleEvents#SERVER_STOPPING} when the server has completely shut down
     */
    Event<Server> SERVER_STOPPED = new Event<>(Server.class, listeners -> (s) -> {
        for (Server listener : listeners) {
            listener.server(s);
        }
    });

    /**
     * @see DatapackSync#onDatapackSync(PlayerList, ServerPlayer) 
     */
    Event<DatapackSync> DATAPACK_SYNC = new Event<>(DatapackSync.class, listeners -> (l, p) -> {
        for (DatapackSync listener : listeners) {
            listener.onDatapackSync(l, p);
        }
    });

    @FunctionalInterface
    interface Server {

        void server(MinecraftServer server);

    }

    @FunctionalInterface
    interface DatapackSync {

        /**
         * Fires when a player joins the server or when the reload command is ran, before tags and crafting recipes are sent to the client. Send datapack data to clients when this event fires.
         *
         * @param playerList List of players
         * @param player Player that the data is synced to. Null if reload was run
         */
        void onDatapackSync(PlayerList playerList, @Nullable ServerPlayer player);

    }

}
