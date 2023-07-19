package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.threetag.palladiumcore.event.LivingEntityEvents;
import net.threetag.palladiumcore.event.PlayerEvents;
import net.threetag.palladiumcore.util.fabric.PlayerUtilImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("ConstantConditions")
@Mixin(Player.class)
public abstract class PlayerMixin implements PlayerUtilImpl.RefreshableDisplayName {

    @Shadow
    public abstract Component getDisplayName();

    @Shadow
    public abstract Component getName();

    @Unique
    private Component palladiumcore_displayname;

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

    @ModifyArg(method = "getDisplayName", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/scores/PlayerTeam;formatNameForTeam(Lnet/minecraft/world/scores/Team;Lnet/minecraft/network/chat/Component;)Lnet/minecraft/network/chat/MutableComponent;"), index = 1)
    private Component injected(Component x) {
        if (this.palladiumcore_displayname == null) {
            this.palladiumCore$refreshDisplayName();
        }
        return this.palladiumcore_displayname;
    }

    @Override
    public void palladiumCore$refreshDisplayName() {
        AtomicReference<Component> name = new AtomicReference<>(this.getName());
        PlayerEvents.NAME_FORMAT.invoker().playerNameFormat((Player) (Object) this, this.getName(), name);
        this.palladiumcore_displayname = name.get();
    }
}
