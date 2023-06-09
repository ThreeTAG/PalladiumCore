package net.threetag.palladiumcore.network.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.threetag.palladiumcore.PalladiumCore;
import net.threetag.palladiumcore.network.ExtendedEntitySpawnData;

import java.util.function.Consumer;

public class SpawnEntityPacket {

    private static final ResourceLocation PACKET_ID = PalladiumCore.id("spawn_entity_packet");

    public static Packet<?> create(Entity entity) {
        if (entity.level().isClientSide()) {
            throw new IllegalStateException("SpawnPacketUtil.create called on the logical client!");
        }
        var buffer = PacketByteBufs.create();
        buffer.writeResourceLocation(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()));
        buffer.writeUUID(entity.getUUID());
        buffer.writeVarInt(entity.getId());
        var position = entity.position();
        buffer.writeDouble(position.x);
        buffer.writeDouble(position.y);
        buffer.writeDouble(position.z);
        buffer.writeFloat(entity.getXRot());
        buffer.writeFloat(entity.getYRot());
        buffer.writeFloat(entity.getYHeadRot());
        var deltaMovement = entity.getDeltaMovement();
        buffer.writeDouble(deltaMovement.x);
        buffer.writeDouble(deltaMovement.y);
        buffer.writeDouble(deltaMovement.z);
        if (entity instanceof ExtendedEntitySpawnData ext) {
            ext.saveAdditionalSpawnData(buffer);
        }
        return ServerPlayNetworking.createS2CPacket(PACKET_ID, buffer);
    }

    @Environment(EnvType.CLIENT)
    public static class Client {

        public static void register() {
            ClientPlayNetworking.registerGlobalReceiver(PACKET_ID, (client, handler, buf, responseSender) -> {
                receive(buf, client::execute);
            });
        }

        public static void receive(FriendlyByteBuf buf, Consumer<Runnable> consumer) {
            var entityTypeId = buf.readResourceLocation();
            var uuid = buf.readUUID();
            var id = buf.readVarInt();
            var x = buf.readDouble();
            var y = buf.readDouble();
            var z = buf.readDouble();
            var xRot = buf.readFloat();
            var yRot = buf.readFloat();
            var yHeadRot = buf.readFloat();
            var deltaX = buf.readDouble();
            var deltaY = buf.readDouble();
            var deltaZ = buf.readDouble();
            buf.retain();
            consumer.accept(() -> {
                if (!BuiltInRegistries.ENTITY_TYPE.containsKey(entityTypeId)) {
                    throw new IllegalStateException("Entity type (" + entityTypeId + ") is unknown, spawning at (" + x + ", " + y + ", " + z + ")");
                }
                if (Minecraft.getInstance().level == null) {
                    throw new IllegalStateException("Client world is null!");
                }
                var entityType = BuiltInRegistries.ENTITY_TYPE.get(entityTypeId);
                var entity = entityType.create(Minecraft.getInstance().level);
                if (entity == null) {
                    throw new IllegalStateException("Created entity is null!");
                }
                entity.setUUID(uuid);
                entity.setId(id);
                entity.syncPacketPositionCodec(x, y, z);
                entity.absMoveTo(x, y, z, yRot, xRot);
                entity.setYHeadRot(yHeadRot);
                entity.setYBodyRot(yHeadRot);
                if (entity instanceof ExtendedEntitySpawnData ext) {
                    ext.loadAdditionalSpawnData(buf);
                }
                buf.release();
                Minecraft.getInstance().level.putNonPlayerEntity(id, entity);
                entity.lerpMotion(deltaX, deltaY, deltaZ);
            });
        }
    }

}
