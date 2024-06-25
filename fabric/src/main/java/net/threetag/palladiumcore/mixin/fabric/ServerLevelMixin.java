package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.threetag.palladiumcore.event.EntityEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("ConstantConditions")
@Mixin(ServerLevel.class)
public class ServerLevelMixin {

    @Inject(at = @At("HEAD"), method = "addPlayer")
    private void addPlayer(ServerPlayer pPlayer, CallbackInfo ci) {
        EntityEvents.JOIN_LEVEL.invoker().entityJoinLevel(pPlayer, (ServerLevel) (Object) this);
    }

}
