package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.threetag.palladiumcore.event.LivingEntityEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("ConstantConditions")
@Mixin(Player.class)
public class PlayerMixin {

    @Unique
    private float palladiumcore_cachedDamageValue = 0F;

    @Unique
    private DamageSource palladiumcore_cachedDamageSource = null;

    @Inject(at = @At("HEAD"),
            method = "die",
            cancellable = true)
    private void die(DamageSource source, CallbackInfo ci) {
        if (LivingEntityEvents.DEATH.invoker().livingEntityDeath((Player) (Object) this, source).cancelsEvent()) {
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"),
            method = "actuallyHurt",
            cancellable = true)
    private void actuallyHurt(DamageSource pDamageSource, float pDamageAmount, CallbackInfo ci) {
        var entity = (LivingEntity) (Object) this;
        AtomicReference<Float> amount = new AtomicReference<>(pDamageAmount);
        if (!entity.isInvulnerableTo(pDamageSource) && (LivingEntityEvents.HURT.invoker().livingEntityHurt(entity, pDamageSource, amount).cancelsEvent() || amount.get() < 0F)) {
            ci.cancel();
        }
        this.palladiumcore_cachedDamageSource = pDamageSource;
        this.palladiumcore_cachedDamageValue = amount.get();
    }

    @ModifyVariable(method = "actuallyHurt", at = @At(value = "STORE", ordinal = 0), ordinal = 0, argsOnly = true)
    private float modifiedDamageAmount(float damageAmount) {
        var entity = (LivingEntity) (Object) this;
        return entity.getDamageAfterArmorAbsorb(this.palladiumcore_cachedDamageSource, this.palladiumcore_cachedDamageValue);
    }

    @Inject(at = @At("HEAD"),
            method = "hurt",
            cancellable = true)
    private void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        var entity = (LivingEntity) (Object) this;
        if (LivingEntityEvents.ATTACK.invoker().livingEntityAttack(entity, source, amount).cancelsEvent()) {
            cir.setReturnValue(false);
        }
    }

}
