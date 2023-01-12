package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.threetag.palladiumcore.event.PlayerEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;setHealth(F)V", shift = At.Shift.AFTER), method = "respawn")
    public void respawn(ServerPlayer player, boolean keepEverything, CallbackInfoReturnable<ServerPlayer> ci) {
        PlayerEvents.RESPAWN.invoker().playerRespawn(player, keepEverything);
    }

}
