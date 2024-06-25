package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.threetag.palladiumcore.event.InputEvents;
import net.threetag.palladiumcore.event.LivingEntityEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
@SuppressWarnings("ConstantConditions")
public class LocalPlayerMixin {

    @Shadow
    public Input input;

    @Inject(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/tutorial/Tutorial;onInput(Lnet/minecraft/client/player/Input;)V"))
    private void aiStep(CallbackInfo ci) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        InputEvents.MOVEMENT_INPUT_UPDATE.invoker().movementInputUpdate(player, this.input);
    }

    @Inject(at = @At("HEAD"),
            method = "hurt")
    private void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        var entity = (LocalPlayer) (Object) this;
        LivingEntityEvents.ATTACK.invoker().livingEntityAttack(entity, source, amount);
    }

}
