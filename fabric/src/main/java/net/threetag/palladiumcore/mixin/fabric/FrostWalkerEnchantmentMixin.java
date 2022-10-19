package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.FrostWalkerEnchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.threetag.palladiumcore.event.BlockEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

@Mixin(FrostWalkerEnchantment.class)
public class FrostWalkerEnchantmentMixin {

    @Inject(method = "onEntityMoved",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD)
    private static void onEntityMoved(LivingEntity living, Level level, BlockPos pos, int levelConflicting, CallbackInfo ci, BlockState blockState, float f, BlockPos.MutableBlockPos mutableBlockPos, Iterator var7, BlockPos blockPos, BlockState blockState2, BlockState blockState3) {
        BlockState placedAgainst = level.getBlockState(blockPos.relative(Direction.DOWN));
        if (BlockEvents.PLACE.invoker().placeBlock(level, blockPos, level.getBlockState(blockPos), placedAgainst, living).cancelsEvent()) {
            ci.cancel();
        }
    }

}
