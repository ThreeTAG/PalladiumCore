package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import net.threetag.palladiumcore.event.PlayerEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/network/protocol/game/ClientboundLoginPacket;playerId()I",
            ordinal = 0),
            method = "handleLogin")
    private void handleLogin(ClientboundLoginPacket pPacket, CallbackInfo ci) {
        PlayerEvents.CLIENT_JOIN.invoker().playerJoin(this.minecraft.player);
    }

}
