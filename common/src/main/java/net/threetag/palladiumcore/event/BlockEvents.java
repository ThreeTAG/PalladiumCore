package net.threetag.palladiumcore.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public interface BlockEvents {

    /**
     * @see BreakBlock#breakBlock(LevelAccessor, BlockPos, BlockState, Player)
     */
    Event<BreakBlock> BREAK = new Event<>(BreakBlock.class, listeners -> (l, po, s, p) -> Event.result(listeners, breakBlock -> breakBlock.breakBlock(l, po, s, p)));

    /**
     * @see PlaceBlock#placeBlock(LevelAccessor, BlockPos, BlockState, BlockState, Entity)
     */
    Event<PlaceBlock> PLACE = new Event<>(PlaceBlock.class, listeners -> (l, po, b1, b2, en) -> Event.result(listeners, placeBlock -> placeBlock.placeBlock(l, po, b1, b2, en)));

    @FunctionalInterface
    interface BreakBlock {

        /**
         * Event that is fired when a Block is about to be broken by a player
         *
         * @param level      The level the block is broken in
         * @param pos        Position of the block
         * @param blockState The blockstate that is being broken
         * @param player     The player breaking the block
         * @return EventResult that determines to cancel the block break or not
         */
        EventResult breakBlock(LevelAccessor level, BlockPos pos, BlockState blockState, Player player);

    }

    @FunctionalInterface
    interface PlaceBlock {

        /**
         * Event that is fired when a Block is about to be placed by a player
         *
         * @param level         The level the block is placed in
         * @param pos           Position of the placement
         * @param placedBlock   The blockstate that is being placed
         * @param placedAgainst The blockstate the new block is placed against
         * @param entity        The entity placing the block. Can be null
         * @return EventResult that determines to cancel the block break or not
         */
        EventResult placeBlock(LevelAccessor level, BlockPos pos, BlockState placedBlock, BlockState placedAgainst, @Nullable Entity entity);

    }

}
