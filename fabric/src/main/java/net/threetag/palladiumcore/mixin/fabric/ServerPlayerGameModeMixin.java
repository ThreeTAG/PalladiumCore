package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.threetag.palladiumcore.event.BlockEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {

    @Shadow
    @Final
    protected ServerPlayer player;

    @Shadow
    protected ServerLevel level;

    @Shadow
    private GameType gameModeForPlayer;

    @Inject(method = "destroyBlock", at = @At("HEAD"), cancellable = true)
    public void destroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
        // Logic from tryHarvestBlock for pre-canceling the event
        boolean cancelEvent = false;
        ItemStack itemstack = this.player.getMainHandItem();
        if (!itemstack.isEmpty() && !itemstack.getItem().canAttackBlock(this.level.getBlockState(pos), level, pos, this.player)) {
            cancelEvent = true;
        }

        if (this.gameModeForPlayer.isBlockPlacingRestricted()) {
            if (this.gameModeForPlayer == GameType.SPECTATOR)
                cancelEvent = true;

            if (!this.player.mayBuild()) {
                if (itemstack.isEmpty() || !itemstack.hasAdventureModeBreakTagForBlock(level.registryAccess().registryOrThrow(Registries.BLOCK), new BlockInWorld(level, pos, false)))
                    cancelEvent = true;
            }
        }

        // Tell client the block is gone immediately then process events
        if (level.getBlockEntity(pos) == null) {
            this.player.connection.send(new ClientboundBlockUpdatePacket(pos, level.getFluidState(pos).createLegacyBlock()));
        }

        // Post the block break event
        BlockState state = level.getBlockState(pos);
        cancelEvent = cancelEvent || BlockEvents.BREAK.invoker().breakBlock(this.level, pos, state, this.player).cancelsEvent();

        // Handle if the event is canceled
        if (cancelEvent) {
            // Let the client know the block still exists
            this.player.connection.send(new ClientboundBlockUpdatePacket(level, pos));

            // Update any tile entity data for this block
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity != null) {
                Packet<?> pkt = blockEntity.getUpdatePacket();
                if (pkt != null) {
                    this.player.connection.send(pkt);
                }
            }

            ci.setReturnValue(false);
        }
    }

}
