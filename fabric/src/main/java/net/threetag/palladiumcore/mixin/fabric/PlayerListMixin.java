package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.threetag.palladiumcore.event.LifecycleEvents;
import net.threetag.palladiumcore.event.PlayerEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("DataFlowIssue")
@Mixin(PlayerList.class)
public class PlayerListMixin {

    @Inject(at = @At("RETURN"), method = "placeNewPlayer")
    private void placeNewPlayerReturn(Connection pNetManager, ServerPlayer pPlayer, CallbackInfo ci) {
        PlayerEvents.JOIN.invoker().playerJoin(pPlayer);
    }

    @Inject(at = @At("HEAD"), method = "remove")
    private void remove(ServerPlayer pPlayer, CallbackInfo ci) {
        PlayerEvents.QUIT.invoker().playerQuit(pPlayer);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;setHealth(F)V", shift = At.Shift.AFTER), method = "respawn")
    private void respawn(ServerPlayer player, boolean keepEverything, CallbackInfoReturnable<ServerPlayer> ci) {
        PlayerEvents.RESPAWN.invoker().playerRespawn(player, keepEverything);
    }

    @Inject(method = "reloadResources", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastAll(Lnet/minecraft/network/protocol/Packet;)V"))
    private void reloadResources(CallbackInfo ci) {
        LifecycleEvents.DATAPACK_SYNC.invoker().onDatapackSync((PlayerList) (Object) this, null);
    }

    @Inject(method = "placeNewPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;send(Lnet/minecraft/network/protocol/Packet;)V", ordinal = 5))
    private void placeNewPlayerSync(Connection netManager, ServerPlayer player, CallbackInfo ci) {
        LifecycleEvents.DATAPACK_SYNC.invoker().onDatapackSync((PlayerList) (Object) this, player);
    }

}
