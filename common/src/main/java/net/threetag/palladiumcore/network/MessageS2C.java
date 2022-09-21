package net.threetag.palladiumcore.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public abstract class MessageS2C extends Message {

    /**
     * Sends message to the given {@link ServerPlayer}
     */
    public void send(ServerPlayer player) {
        this.getType().getNetworkManager().sendToPlayer(player, this);
    }

    /**
     * Sends message to every {@link ServerPlayer} that is currently tracking the given {@link Entity}
     */
    public void sendToTracking(Entity entity) {
        this.getType().getNetworkManager().sendToTracking(entity, this);
    }

    /**
     * Sends message to every {@link ServerPlayer} that is currently tracking the given {@link Entity} AND the player themselves
     */
    public void sendToTrackingAndSelf(ServerPlayer player) {
        this.getType().getNetworkManager().sendToTrackingAndSelf(player, this);
    }

    /**
     * Sends message to every player in the given dimension/{@link Level}
     */
    public void sendToDimension(Level level) {
        this.getType().getNetworkManager().sendToDimension(level, this);
    }

}
