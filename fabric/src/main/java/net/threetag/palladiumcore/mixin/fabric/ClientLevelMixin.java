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
    private void addEntity(int pEntityId, Entity pEntityToSpawn, CallbackInfo ci) {
        EntityEvents.JOIN_LEVEL.invoker().entityJoinLevel(pEntityToSpawn, (ClientLevel) (Object) this);
    }

}
