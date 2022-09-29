package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.client.player.RemotePlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.threetag.palladiumcore.event.LivingEntityEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("ConstantConditions")
@Mixin(RemotePlayer.class)
public class RemotePlayerMixin {

    @Inject(at = @At("HEAD"),
            method = "hurt")
    private void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        var entity = (RemotePlayer) (Object) this;
        LivingEntityEvents.ATTACK.invoker().livingEntityAttack(entity, source, amount);
    }

}
