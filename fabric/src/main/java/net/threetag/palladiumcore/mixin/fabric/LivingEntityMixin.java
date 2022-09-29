package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.threetag.palladiumcore.event.LivingEntityEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("ConstantConditions")
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    public abstract ItemStack getItemInHand(InteractionHand hand);

    @Shadow
    protected ItemStack useItem;

    @Shadow
    protected int useItemRemaining;

    @Shadow
    protected abstract void detectEquipmentUpdates();

    @Shadow
    public abstract InteractionHand getUsedItemHand();

    @Shadow
    protected abstract void updatingUsingItem();

    @Shadow
    public abstract void stopUsingItem();

    @Inject(at = @At("HEAD"),
            method = "die",
            cancellable = true)
    private void die(DamageSource source, CallbackInfo ci) {
        if (LivingEntityEvents.DEATH.invoker().livingEntityDeath((LivingEntity) (Object) this, source).cancelsEvent()) {
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"),
            method = "actuallyHurt",
            cancellable = true)
    private void actuallyHurt(DamageSource pDamageSource, float pDamageAmount, CallbackInfo ci) {
        var entity = (LivingEntity) (Object) this;
        if (!entity.isInvulnerableTo(pDamageSource) && LivingEntityEvents.HURT.invoker().livingEntityHurt(entity, pDamageSource, pDamageAmount).cancelsEvent()) {
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"),
            method = "hurt",
            cancellable = true)
    private void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        var entity = (LivingEntity) (Object) this;

        if (!(entity instanceof Player || !LivingEntityEvents.HURT.invoker().livingEntityHurt(entity, source, amount).cancelsEvent())) {
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo callbackInfo) {
        LivingEntity entity = (LivingEntity) (Object) this;
        LivingEntityEvents.TICK.invoker().livingEntityTick(entity);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseDuration()I", shift = At.Shift.AFTER),
            method = "startUsingItem",
            cancellable = true)
    private void startUsingItem(InteractionHand hand, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        ItemStack stack = this.getItemInHand(hand);
        AtomicInteger duration = new AtomicInteger(stack.getUseDuration());
        if (LivingEntityEvents.ITEM_USE_START.invoker().livingEntityItemUse(entity, stack, duration).cancelsEvent() || duration.get() <= 0) {
            this.useItem = ItemStack.EMPTY;
            this.useItemRemaining = 0;
            ci.cancel();
        } else {
            this.useItemRemaining = duration.get();
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;updateUsingItem(Lnet/minecraft/world/item/ItemStack;)V"),
            method = "updatingUsingItem")
    private void updatingUsingItem(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        ItemStack stack = this.getItemInHand(this.getUsedItemHand());
        AtomicInteger duration = new AtomicInteger(this.useItemRemaining);
        if (LivingEntityEvents.ITEM_USE_TICK.invoker().livingEntityItemUse(entity, stack, duration).cancelsEvent()) {
            duration.set(-1);
        }
        this.useItemRemaining = duration.get();
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;releaseUsing(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;I)V"),
            method = "releaseUsingItem",
            cancellable = true)
    private void releaseUsingItem(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        AtomicInteger duration = new AtomicInteger(this.useItemRemaining);
        if (LivingEntityEvents.ITEM_USE_STOP.invoker().livingEntityItemUse(entity, this.useItem, duration).cancelsEvent()) {
            ci.cancel();
            if (this.useItem.useOnRelease()) {
                this.updatingUsingItem();
            }
            this.stopUsingItem();
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;finishUsingItem(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/item/ItemStack;", shift = At.Shift.AFTER),
            method = "completeUsingItem")
    private void completeUsingItem(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        AtomicInteger duration = new AtomicInteger(this.useItemRemaining);
        LivingEntityEvents.ITEM_USE_FINISH.invoker().livingEntityItemUseFinish(entity, this.useItem, duration);
    }

}
