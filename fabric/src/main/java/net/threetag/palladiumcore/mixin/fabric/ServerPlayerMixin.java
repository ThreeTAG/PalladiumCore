package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.threetag.palladiumcore.event.LivingEntityEvents;
import net.threetag.palladiumcore.event.PlayerEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("ConstantConditions")
@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;gameEvent(Lnet/minecraft/world/level/gameevent/GameEvent;)V", shift = At.Shift.AFTER), method = "die", cancellable = true)
    private void die(DamageSource source, CallbackInfo ci) {
        if (LivingEntityEvents.DEATH.invoker().livingEntityDeath((ServerPlayer) (Object) this, source).cancelsEvent()) {
            ci.cancel();
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;sendAllPlayerInfo(Lnet/minecraft/server/level/ServerPlayer;)V", shift = At.Shift.AFTER), method = "teleportTo(Lnet/minecraft/server/level/ServerLevel;DDDFF)V")
    private void teleportTo(ServerLevel newLevel, double x, double y, double z, float yaw, float pitch, CallbackInfo ci) {
        PlayerEvents.CHANGED_DIMENSION.invoker().playerChangedDimension((ServerPlayer) (Object) this, newLevel.dimension());
    }

    @Inject(method = "changeDimension",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;send(Lnet/minecraft/network/protocol/Packet;)V",
                    shift = At.Shift.AFTER,
                    ordinal = 5))
    private void changeDimension(ServerLevel destination, CallbackInfoReturnable<Entity> cir) {
        PlayerEvents.CHANGED_DIMENSION.invoker().playerChangedDimension((ServerPlayer) (Object) this, destination.dimension());
    }

    @Inject(at = @At("TAIL"), method = "restoreFrom")
    private void restoreFrom(ServerPlayer that, boolean keepEverything, CallbackInfo ci) {
        PlayerEvents.CLONE.invoker().playerClone(that, (ServerPlayer) (Object) this, !keepEverything);
    }

}
