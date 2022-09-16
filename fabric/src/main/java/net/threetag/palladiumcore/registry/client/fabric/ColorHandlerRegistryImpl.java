package net.threetag.palladiumcore.registry.client.fabric;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class ColorHandlerRegistryImpl {

    @SafeVarargs
    public static void registerItemColors(ItemColor color, Supplier<? extends ItemLike>... items) {
        ItemLike[] items1 = new ItemLike[items.length];
        for(int i = 0; i < items.length; i++) {
            items1[i] = items[i].get();
        }
        ColorProviderRegistry.ITEM.register(color, items1);
    }

    @SafeVarargs
    public static void registerBlockColors(BlockColor color, Supplier<? extends Block>... blocks) {
        Block[] blocks1 = new Block[blocks.length];
        for(int i = 0; i < blocks.length; i++) {
            blocks1[i] = blocks[i].get();
        }
        ColorProviderRegistry.BLOCK.register(color, blocks1);
    }
}
