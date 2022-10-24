package net.threetag.palladiumcore.registry.client.forge;

import com.google.common.collect.Lists;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.threetag.palladiumcore.PalladiumCore;
import net.threetag.palladiumcore.item.PalladiumSpawnEggItem;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = PalladiumCore.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ColorHandlerRegistryImpl {

    private static final List<Pair<ItemColor, Supplier<? extends ItemLike>[]>> ITEM_COLORS = Lists.newArrayList();
    private static final List<Pair<BlockColor, Supplier<? extends Block>[]>> BLOCK_COLORS = Lists.newArrayList();

    @SafeVarargs
    public static void registerItemColors(ItemColor color, Supplier<? extends ItemLike>... items) {
        ITEM_COLORS.add(Pair.of(color, items));
    }

    @SafeVarargs
    public static void registerBlockColors(BlockColor color, Supplier<? extends Block>... blocks) {
        BLOCK_COLORS.add(Pair.of(color, blocks));
    }

    @SubscribeEvent
    public static void itemColors(RegisterColorHandlersEvent.Item e) {
        for (PalladiumSpawnEggItem egg : PalladiumSpawnEggItem.MOD_EGGS) {
            e.register((stack, layer) -> egg.getColor(layer), egg);
        }

        for (Pair<ItemColor, Supplier<? extends ItemLike>[]> pair : ITEM_COLORS) {
            ItemLike[] items1 = new ItemLike[pair.getRight().length];
            for (int i = 0; i < pair.getRight().length; i++) {
                items1[i] = pair.getRight()[i].get();
            }
            e.register(pair.getLeft(), items1);
        }
    }

    @SubscribeEvent
    public static void blockColors(RegisterColorHandlersEvent.Block e) {
        for (Pair<BlockColor, Supplier<? extends Block>[]> pair : BLOCK_COLORS) {
            Block[] blocks1 = new Block[pair.getRight().length];
            for (int i = 0; i < pair.getRight().length; i++) {
                blocks1[i] = pair.getRight()[i].get();
            }
            e.register(pair.getLeft(), blocks1);
        }
    }

}
