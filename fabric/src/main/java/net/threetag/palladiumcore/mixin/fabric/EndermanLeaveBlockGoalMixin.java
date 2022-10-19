package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.threetag.palladiumcore.event.BlockEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderMan.EndermanLeaveBlockGoal.class)
public class EndermanLeaveBlockGoalMixin {

    @Shadow
    @Final
    private EnderMan enderman;

    @Inject(method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"),
            cancellable = true)
    public void tick(CallbackInfo ci) {
        RandomSource randomsource = this.enderman.getRandom();
        Level level = this.enderman.level;
        int i = Mth.floor(this.enderman.getX() - 1.0D + randomsource.nextDouble() * 2.0D);
        int j = Mth.floor(this.enderman.getY() + randomsource.nextDouble() * 2.0D);
        int k = Mth.floor(this.enderman.getZ() - 1.0D + randomsource.nextDouble() * 2.0D);
        BlockPos blockpos = new BlockPos(i, j, k);
        BlockPos placedPos = blockpos.below();
        BlockState placedBlock = level.getBlockState(placedPos);
        BlockState placedAgainst = this.enderman.level.getBlockState(placedPos.relative(Direction.DOWN));

        if (BlockEvents.PLACE.invoker().placeBlock(this.enderman.level, placedPos, placedBlock, placedAgainst, this.enderman).cancelsEvent()) {
            ci.cancel();
        }
    }

}
