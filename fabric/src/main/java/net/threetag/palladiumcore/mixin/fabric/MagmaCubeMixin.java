package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.MagmaCube;
import net.threetag.palladiumcore.event.LivingEntityEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MagmaCube.class)
public class MagmaCubeMixin {

    @Inject(method = "jumpFromGround", at = @At("RETURN"))
    private void jumpFromGround(CallbackInfo ci) {
        LivingEntityEvents.JUMP.invoker().livingEntityJump((LivingEntity) (Object) this);
    }

}
