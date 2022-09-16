package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.client.server.IntegratedServer;
import net.minecraft.server.MinecraftServer;
import net.threetag.palladiumcore.event.LifecycleEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("ConstantConditions")
@Mixin(IntegratedServer.class)
public class IntegratedServerMixin {

    @Inject(at = @At("RETURN"), method = "initServer")
    private void initServer(CallbackInfoReturnable<Boolean> ci) {
        var server = (MinecraftServer) (Object) this;
        LifecycleEvents.SERVER_STARTING.invoker().server(server);
    }

}
