package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.world.entity.Entity;
import net.threetag.palladiumcore.event.EntityEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;tick()V"), method = "rideTick")
    private void tickPre(CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        EntityEvents.TICK_PRE.invoker().entityTick(entity);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;tick()V", shift = At.Shift.AFTER), method = "rideTick")
    private void tickPost(CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        EntityEvents.TICK_POST.invoker().entityTick(entity);
    }

}
