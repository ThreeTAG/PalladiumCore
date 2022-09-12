package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.threetag.palladiumcore.event.PlayerEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class PlayerListMixin {

    @Inject(at = @At("RETURN"), method = "placeNewPlayer")
    private void placeNewPlayer(Connection pNetManager, ServerPlayer pPlayer, CallbackInfo ci) {
        PlayerEvents.JOIN.invoker().playerJoin(pPlayer);
    }

    @Inject(at = @At("HEAD"), method = "remove")
    private void remove(ServerPlayer pPlayer, CallbackInfo ci) {
        PlayerEvents.QUIT.invoker().playerQuit(pPlayer);
    }

}
