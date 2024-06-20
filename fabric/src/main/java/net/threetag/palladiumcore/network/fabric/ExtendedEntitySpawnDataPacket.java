package net.threetag.palladiumcore.network.fabric;

import io.netty.buffer.Unpooled;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.threetag.palladiumcore.PalladiumCore;
import net.threetag.palladiumcore.network.ExtendedEntitySpawnData;
import net.threetag.palladiumcore.network.NetworkManager;

import java.util.function.Consumer;

public record ExtendedEntitySpawnDataPacket(int entityId, byte[] customPayload) implements CustomPacketPayload {

    public static void register() {
        NetworkManager.get().registerS2C(TYPE, STREAM_CODEC, ExtendedEntitySpawnDataPacket::handle);
    }

    public static final Type<ExtendedEntitySpawnDataPacket> TYPE = new Type<>(PalladiumCore.id("extended_entity_spawn_data"));
    public static final StreamCodec<FriendlyByteBuf, byte[]> UNBOUNDED_BYTE_ARRAY = new StreamCodec<>() {
        public byte[] decode(FriendlyByteBuf buf) {
            return buf.readByteArray();
        }

        public void encode(FriendlyByteBuf buf, byte[] data) {
            buf.writeByteArray(data);
        }
    };
    public static final StreamCodec<FriendlyByteBuf, ExtendedEntitySpawnDataPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            ExtendedEntitySpawnDataPacket::entityId,
            UNBOUNDED_BYTE_ARRAY,
            ExtendedEntitySpawnDataPacket::customPayload,
            ExtendedEntitySpawnDataPacket::new);

    public ExtendedEntitySpawnDataPacket(Entity e) {
        this(e.getId(), writeCustomData(e));
    }

    private static byte[] writeCustomData(final Entity entity) {
        if (!(entity instanceof ExtendedEntitySpawnData additionalSpawnData)) {
            return new byte[0];
        }

        return writeCustomData(additionalSpawnData::saveAdditionalSpawnData, entity.registryAccess());
    }

    private static byte[] writeCustomData(Consumer<RegistryFriendlyByteBuf> dataWriter, RegistryAccess registryAccess) {
        final RegistryFriendlyByteBuf buf = new RegistryFriendlyByteBuf(Unpooled.buffer(), registryAccess);
        try {
            dataWriter.accept(buf);
            return buf.array();
        } finally {
            buf.release();
        }
    }

    @Override
    public Type<?> type() {
        return TYPE;
    }

    public static void handle(ExtendedEntitySpawnDataPacket advancedAddEntityPayload, NetworkManager.Context context) {
        try {
            Entity entity = context.getPlayer().level().getEntity(advancedAddEntityPayload.entityId());
            if (entity instanceof ExtendedEntitySpawnData entityAdditionalSpawnData) {
                final RegistryFriendlyByteBuf buf = new RegistryFriendlyByteBuf(Unpooled.wrappedBuffer(advancedAddEntityPayload.customPayload()), entity.registryAccess());
                try {
                    entityAdditionalSpawnData.loadAdditionalSpawnData(buf);
                } finally {
                    buf.release();
                }
            }
        } catch (Throwable t) {
            PalladiumCore.LOGGER.error(I18n.get("neoforge.network.advanced_add_entity.failed", t.getMessage()));
        }
    }
}
