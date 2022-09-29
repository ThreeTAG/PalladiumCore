package net.threetag.palladiumcore.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.threetag.palladiumcore.event.EntityEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@SuppressWarnings("ConstantConditions")
@Mixin(LightningBolt.class)
public class LightningBoltMixin {

    @Inject(at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/level/Level;getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;", ordinal = 1, shift = At.Shift.BY, by = 1), method = "tick", locals = LocalCapture.CAPTURE_FAILHARD)
    private void tick(CallbackInfo ci, List<Entity> list) {
        EntityEvents.LIGHTNING_STRIKE.invoker().lightningStrike(list, (LightningBolt) (Object) this);
    }

}
