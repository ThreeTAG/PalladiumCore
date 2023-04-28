package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import net.threetag.palladiumcore.event.BlockEvents;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {

    @Shadow
    @Nullable
    protected abstract BlockState getPlacementState(BlockPlaceContext context);

    @Inject(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/context/BlockPlaceContext;getClickedPos()Lnet/minecraft/core/BlockPos;"), cancellable = true)
    private void place(BlockPlaceContext context, CallbackInfoReturnable<InteractionResult> cir) {
        if (BlockEvents.PLACE.invoker().placeBlock(context.getLevel(), context.getClickedPos(), context.getLevel().getBlockState(context.getClickedPos()), this.getPlacementState(context), context.getPlayer()).cancelsEvent()) {
            cir.setReturnValue(InteractionResult.FAIL);
        }
    }

}
