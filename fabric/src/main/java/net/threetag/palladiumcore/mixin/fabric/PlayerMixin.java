package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.threetag.palladiumcore.event.LivingEntityEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("ConstantConditions")
@Mixin(Player.class)
public class PlayerMixin {

    @Inject(at = @At("HEAD"),
            method = "die",
            cancellable = true)
    private void die(DamageSource source, CallbackInfo ci) {
        if (LivingEntityEvents.DEATH.invoker().livingEntityDeath((Player) (Object) this, source).cancelsEvent()) {
            ci.cancel();
        }
    }

}
