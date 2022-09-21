package net.threetag.palladiumcore.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

/**
 * <p>Can be implemented on {@link net.minecraft.world.entity.Entity} to append additional data when being sent to the client</p>
 * <p></p><b>You must also override {@link Entity#getAddEntityPacket()} and return {@link NetworkManager#createAddEntityPacket(Entity)}</b>
 * Example Usage:
 * <pre>{@code
 * @Override
 * public Packet<?> getAddEntityPacket() {
 *    return NetworkManager.createAddEntityPacket(this);
 * }
 * }</pre></p>
 */
public interface ExtendedEntitySpawnData {

    /**
     * Called by the server, data must be added the {@link FriendlyByteBuf}
     */
    void saveAdditionalSpawnData(FriendlyByteBuf buf);

    /**
     * Called by the client, data can be retrieved by the {@link FriendlyByteBuf}
     */
    void loadAdditionalSpawnData(FriendlyByteBuf buf);

}
