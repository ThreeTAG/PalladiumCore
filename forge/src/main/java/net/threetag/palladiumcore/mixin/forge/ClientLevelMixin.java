package net.threetag.palladiumcore.mixin.forge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.threetag.palladiumcore.event.ClientTickEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("ConstantConditions")
@Mixin(ClientLevel.class)
public class ClientLevelMixin {

    @Inject(at = @At("HEAD"), method = "tickEntities")
    private void tickEntitiesPre(CallbackInfo ci) {
        ClientTickEvents.CLIENT_LEVEL_PRE.invoker().clientLevelTick(Minecraft.getInstance(), (ClientLevel) (Object) this);
    }

    @Inject(at = @At("RETURN"), method = "tickEntities")
    private void tickEntitiesPost(CallbackInfo ci) {
        ClientTickEvents.CLIENT_LEVEL_POST.invoker().clientLevelTick(Minecraft.getInstance(), (ClientLevel) (Object) this);
    }

}
