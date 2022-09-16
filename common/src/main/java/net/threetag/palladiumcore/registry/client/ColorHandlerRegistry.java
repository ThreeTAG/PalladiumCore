package net.threetag.palladiumcore.registry.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class ColorHandlerRegistry {

    @SafeVarargs
    @ExpectPlatform
    public static void registerItemColors(ItemColor color, Supplier<? extends ItemLike>... items) {
        throw new AssertionError();
    }

    @SafeVarargs
    @ExpectPlatform
    public static void registerBlockColors(BlockColor color, Supplier<? extends Block>... blocks) {
        throw new AssertionError();
    }

}
