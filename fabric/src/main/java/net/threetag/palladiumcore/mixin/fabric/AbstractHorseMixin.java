package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.phys.Vec3;
import net.threetag.palladiumcore.event.LivingEntityEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractHorse.class)
public class AbstractHorseMixin {

    @Inject(method = "executeRidersJump", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/horse/AbstractHorse;setIsJumping(Z)V", shift = At.Shift.AFTER))
    private void executeRidersJump(float playerJumpPendingScale, Vec3 travelVector, CallbackInfo ci) {
        LivingEntityEvents.JUMP.invoker().livingEntityJump((LivingEntity) (Object) this);
    }

}
