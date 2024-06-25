package net.threetag.palladiumcore.network;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to create a network channel to send data between server/client
 * Example Usage:
 * <pre>{@code
 *   public static final SimpleNetworkManager NETWORK = SimpleNetworkManager.create(Palladium.MOD_ID);
 *
 *   public static final MessageType SYNC_POWERS = NETWORK.registerS2C("sync_powers", SyncPowersMessage::new);
 *   public static final MessageType ABILITY_KEY_PRESSED = NETWORK.registerC2S("ability_key_pressed", AbilityKeyPressedMessage::new);
 * }</pre>
 */
public abstract class NetworkManager {

    protected final ResourceLocation channelName;
    protected final Map<String, MessageType> toServer = new HashMap<>();
    protected final Map<String, MessageType> toClient = new HashMap<>();

    /**
     * @param channelName Unique identifier for this new network channel
     * @return A new {@link NetworkManager}
     */
    @ExpectPlatform
    public static NetworkManager create(ResourceLocation channelName) {
        throw new AssertionError();
    }

    protected NetworkManager(ResourceLocation channelName) {
        this.channelName = channelName;
    }

    /**
     * Registers a message which is sent from the server to clients
     *
     * @param id      Unique ID of this message
     * @param decoder Decoder instance which reads a {@link FriendlyByteBuf}
     * @return A new {@link MessageType} used for the new message
     */
    public MessageType registerS2C(String id, MessageDecoder<MessageS2C> decoder) {
        var msgType = new MessageType(id, this, decoder, false);
        this.toClient.put(id, msgType);
        return msgType;
    }

    /**
     * Registers a message which is sent clients to the server
     *
     * @param id      Unique ID of this message
     * @param decoder Decoder instance which reads a {@link FriendlyByteBuf}
     * @return A new {@link MessageType} used for the new message
     */
    public MessageType registerC2S(String id, MessageDecoder<MessageC2S> decoder) {
        var msgType = new MessageType(id, this, decoder, true);
        this.toServer.put(id, msgType);
        return msgType;
    }

    /**
     * Sends message to every player online
     */
    public abstract void sendToServer(MessageC2S message);

    /**
     * Sends message to the given {@link ServerPlayer}
     */
    public abstract void sendToPlayer(ServerPlayer player, MessageS2C message);

    /**
     * Sends message to every {@link ServerPlayer} that is currently tracking the given {@link Entity}
     */
    public abstract void sendToTracking(Entity entity, MessageS2C message);

    /**
     * Sends message to every {@link ServerPlayer} that is currently tracking the given {@link Entity} AND the player themselves
     */
    public abstract void sendToTrackingAndSelf(ServerPlayer player, MessageS2C message);

    /**
     * Sends message to every player in the given dimension/{@link Level}
     */
    public void sendToDimension(Level level, MessageS2C message) {
        if (!level.isClientSide) {
            for (Player player : level.players()) {
                this.sendToPlayer((ServerPlayer) player, message);
            }
        }
    }

    @ExpectPlatform
    public static Packet<ClientGamePacketListener> createAddEntityPacket(Entity entity) {
        throw new AssertionError();
    }

    @FunctionalInterface
    public interface MessageDecoder<T extends Message> {

        T decode(FriendlyByteBuf buf);

    }

}
