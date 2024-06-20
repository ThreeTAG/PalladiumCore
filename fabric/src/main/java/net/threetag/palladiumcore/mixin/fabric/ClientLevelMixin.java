package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.threetag.palladiumcore.event.EntityEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("ConstantConditions")
@Mixin(ClientLevel.class)
public class ClientLevelMixin {

    @Inject(at = @At("HEAD"), method = "addEntity")
    private void addEntity(Entity entity, CallbackInfo ci) {
        EntityEvents.JOIN_LEVEL.invoker().entityJoinLevel(entity, (ClientLevel) (Object) this);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;tick()V"), method = "tickNonPassenger")
    private void tickPre(Entity entity, CallbackInfo ci) {
        EntityEvents.TICK_PRE.invoker().entityTick(entity);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;tick()V", shift = At.Shift.AFTER), method = "tickNonPassenger")
    private void tickPost(Entity entity, CallbackInfo ci) {
        EntityEvents.TICK_POST.invoker().entityTick(entity);
    }

}
