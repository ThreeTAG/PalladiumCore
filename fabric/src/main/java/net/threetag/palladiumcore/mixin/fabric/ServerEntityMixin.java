package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.threetag.palladiumcore.event.PlayerEvents;
import net.threetag.palladiumcore.network.ExtendedEntitySpawnData;
import net.threetag.palladiumcore.network.fabric.ExtendedEntitySpawnDataPacket;
import net.threetag.palladiumcore.network.fabric.PacketAndPayloadAcceptor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ServerEntity.class)
public class ServerEntityMixin {

    @Shadow
    @Final
    private Entity entity;

    @Inject(at = @At("RETURN"), method = "addPairing")
    private void addPairing(ServerPlayer pPlayer, CallbackInfo ci) {
        PlayerEvents.START_TRACKING.invoker().playerTracking(pPlayer, this.entity);
    }

    @Inject(at = @At("RETURN"), method = "removePairing")
    private void removePairing(ServerPlayer pPlayer, CallbackInfo ci) {
        PlayerEvents.START_TRACKING.invoker().playerTracking(pPlayer, this.entity);
    }

    @SuppressWarnings({"rawtypes", "UnnecessaryLocalVariable", "unchecked"})
    @Inject(at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V", ordinal = 0, shift = At.Shift.AFTER), method = "sendPairingData")
    private void sendPairingData(ServerPlayer player, Consumer<Packet<ClientGamePacketListener>> consumer, CallbackInfo ci) {
        if (this.entity instanceof ExtendedEntitySpawnData) {
            Consumer consumer_ = consumer;
            PacketAndPayloadAcceptor<ClientGamePacketListener> test = new PacketAndPayloadAcceptor<>(consumer_);
            test.accept(new ExtendedEntitySpawnDataPacket(this.entity));
        }
    }

}
