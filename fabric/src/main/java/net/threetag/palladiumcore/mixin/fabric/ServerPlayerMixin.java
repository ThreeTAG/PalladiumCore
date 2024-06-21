package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.DimensionTransition;
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

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;gameEvent(Lnet/minecraft/core/Holder;)V", shift = At.Shift.AFTER), method = "die", cancellable = true)
    private void die(DamageSource source, CallbackInfo ci) {
        if (LivingEntityEvents.DEATH.invoker().livingEntityDeath((ServerPlayer) (Object) this, source).cancelsEvent()) {
            ci.cancel();
        }
    }

    @Inject(method = "changeDimension",
            at = @At(value = "RETURN", ordinal = 2))
    private void changeDimension(DimensionTransition dimensionTransition, CallbackInfoReturnable<Entity> cir) {
        PlayerEvents.CHANGED_DIMENSION.invoker().playerChangedDimension((ServerPlayer) (Object) this, dimensionTransition.newLevel().dimension());
    }

    @Inject(at = @At("TAIL"), method = "restoreFrom")
    private void restoreFrom(ServerPlayer that, boolean keepEverything, CallbackInfo ci) {
        PlayerEvents.CLONE.invoker().playerClone(that, (ServerPlayer) (Object) this, !keepEverything);
    }

}
