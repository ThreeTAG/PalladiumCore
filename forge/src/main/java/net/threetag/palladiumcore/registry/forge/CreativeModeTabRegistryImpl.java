package net.threetag.palladiumcore.registry.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.threetag.palladiumcore.PalladiumCore;
import net.threetag.palladiumcore.registry.CreativeModeTabRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = PalladiumCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeModeTabRegistryImpl {

    private static final Map<TabSupplier, Consumer<CreativeModeTab.Builder>> TABS = new HashMap<>();
    private static final Map<Supplier<CreativeModeTab>, Consumer<CreativeModeTabRegistry.ItemGroupEntries>> TAB_MODIFICATIONS = new HashMap<>();

    public static Supplier<CreativeModeTab> create(ResourceLocation id, Consumer<CreativeModeTab.Builder> builderConsumer) {
        var supplier = new TabSupplier(id);
        TABS.put(supplier, builderConsumer);
        return supplier;
    }

    public static void addToTab(Supplier<CreativeModeTab> tab, Consumer<CreativeModeTabRegistry.ItemGroupEntries> entriesConsumer) {
        TAB_MODIFICATIONS.put(tab, entriesConsumer);
    }

    @SubscribeEvent
    public static void buildContents(CreativeModeTabEvent.Register e) {
        for (Map.Entry<TabSupplier, Consumer<CreativeModeTab.Builder>> entry : TABS.entrySet()) {
            entry.getKey().tab = e.registerCreativeModeTab(entry.getKey().id, entry.getValue());
        }
    }

    @SubscribeEvent
    public static void buildContents(CreativeModeTabEvent.BuildContents e) {
        for (Map.Entry<Supplier<CreativeModeTab>, Consumer<CreativeModeTabRegistry.ItemGroupEntries>> entry : TAB_MODIFICATIONS.entrySet()) {
            if (entry.getKey().get() == e.getTab()) {
                entry.getValue().accept(new ItemGroupEntriesWrapper(e));
            }
        }
    }

    public static class TabSupplier implements Supplier<CreativeModeTab> {

        private final ResourceLocation id;
        private CreativeModeTab tab;

        public TabSupplier(ResourceLocation id) {
            this.id = id;
        }

        @Override
        public CreativeModeTab get() {
            Objects.requireNonNull(this.tab, () -> "Creative mode tab not present: " + this.id);
            return this.tab;
        }
    }

    private record ItemGroupEntriesWrapper(
            CreativeModeTabEvent.BuildContents e) implements CreativeModeTabRegistry.ItemGroupEntries {

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
            for (ItemLike itemLike : item) {
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
            for (ItemStack stack : item) {
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
