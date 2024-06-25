package net.threetag.palladiumcore.registry.forge;

import com.google.common.collect.Lists;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.threetag.palladiumcore.PalladiumCore;
import net.threetag.palladiumcore.registry.CreativeModeTabRegistry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = PalladiumCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeModeTabRegistryImpl {

    private static final Map<Supplier<CreativeModeTab>, Consumer<CreativeModeTabRegistry.ItemGroupEntries>> TAB_MODIFICATIONS = new HashMap<>();

    public static CreativeModeTab create(Component title, Supplier<ItemStack> icon) {
        return CreativeModeTab.builder().title(title).icon(icon).build();
    }

    public static CreativeModeTab create(Consumer<CreativeModeTab.Builder> builderConsumer) {
        var builder = CreativeModeTab.builder();
        builderConsumer.accept(builder);
        return builder.build();
    }

    public static void addToTab(Supplier<CreativeModeTab> tab, Consumer<CreativeModeTabRegistry.ItemGroupEntries> entriesConsumer) {
        TAB_MODIFICATIONS.put(tab, entriesConsumer);
    }

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent e) {
        for (Map.Entry<Supplier<CreativeModeTab>, Consumer<CreativeModeTabRegistry.ItemGroupEntries>> entry : TAB_MODIFICATIONS.entrySet()) {
            if (entry.getKey().get() == e.getTab()) {
                entry.getValue().accept(new ItemGroupEntriesWrapper(e));
            }
        }
    }

    private record ItemGroupEntriesWrapper(
            BuildCreativeModeTabContentsEvent e) implements CreativeModeTabRegistry.ItemGroupEntries {

        public ItemStack findLast(ItemLike itemLike) {
            ItemStack stack = null;

            for (Map.Entry<ItemStack, CreativeModeTab.TabVisibility> entry : this.e.getEntries()) {
                if (entry.getKey().is(itemLike.asItem())) {
                    stack = entry.getKey();
                }
            }

            return stack;
        }

        public ItemStack findFirst(ItemLike itemLike) {
            for (Map.Entry<ItemStack, CreativeModeTab.TabVisibility> entry : this.e.getEntries()) {
                if (entry.getKey().is(itemLike.asItem())) {
                    return entry.getKey();
                }
            }
            return null;
        }

        @Override
        public void add(ItemLike... item) {
            for (ItemLike itemLike : item) {
                this.e.accept(itemLike);
            }
        }

        @Override
        public void add(CreativeModeTab.TabVisibility visibility, ItemLike... item) {
            for (ItemLike itemLike : item) {
                this.e.accept(itemLike, visibility);
            }
        }

        @Override
        public void addAfter(ItemLike afterLast, ItemLike... item) {
            this.addAfter(afterLast, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, item);
        }

        @Override
        public void addAfter(ItemLike afterLast, CreativeModeTab.TabVisibility visibility, ItemLike... item) {
            for (ItemLike itemLike : Lists.reverse(Arrays.asList(item))) {
                this.e.getEntries().putAfter(findLast(afterLast), itemLike.asItem().getDefaultInstance(), visibility);
            }
        }

        @Override
        public void addBefore(ItemLike beforeFirst, ItemLike... item) {
            this.addBefore(beforeFirst, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, item);
        }

        @Override
        public void addBefore(ItemLike beforeFirst, CreativeModeTab.TabVisibility visibility, ItemLike... item) {
            for (ItemLike itemLike : item) {
                this.e.getEntries().putBefore(findFirst(beforeFirst), itemLike.asItem().getDefaultInstance(), visibility);
            }
        }

        @Override
        public void add(ItemStack... stack) {
            for (ItemStack itemStack : stack) {
                this.e.accept(itemStack);
            }
        }

        @Override
        public void add(CreativeModeTab.TabVisibility visibility, ItemStack... stack) {
            for (ItemStack itemStack : stack) {
                this.e.accept(itemStack, visibility);
            }
        }

        @Override
        public void addAfter(ItemLike afterLast, ItemStack... item) {
            this.addAfter(afterLast, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, item);
        }

        @Override
        public void addAfter(ItemLike afterLast, CreativeModeTab.TabVisibility visibility, ItemStack... item) {
            for (ItemStack stack : Lists.reverse(Arrays.asList(item))) {
                this.e.getEntries().putAfter(findLast(afterLast), stack, visibility);
            }
        }

        @Override
        public void addBefore(ItemLike beforeFirst, ItemStack... item) {
            this.addBefore(beforeFirst, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, item);
        }

        @Override
        public void addBefore(ItemLike beforeFirst, CreativeModeTab.TabVisibility visibility, ItemStack... item) {
            for (ItemStack stack : item) {
                this.e.getEntries().putBefore(findFirst(beforeFirst), stack, visibility);
            }
        }
    }

}
