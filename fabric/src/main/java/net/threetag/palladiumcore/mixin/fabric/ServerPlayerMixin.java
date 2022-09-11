package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.threetag.palladiumcore.event.LivingEntityEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("ConstantConditions")
@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;gameEvent(Lnet/minecraft/world/level/gameevent/GameEvent;)V", shift = At.Shift.AFTER),
            method = "die",
            cancellable = true)
    private void die(DamageSource source, CallbackInfo ci) {
        if (LivingEntityEvents.DEATH.invoker().livingEntityDeath((ServerPlayer) (Object) this, source).cancelsEvent()) {
            ci.cancel();
        }
    }

}
