package net.threetag.palladiumcore.registry.fabric;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.threetag.palladiumcore.registry.CreativeModeTabRegistry;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CreativeModeTabRegistryImpl {

    public static Supplier<CreativeModeTab> create(ResourceLocation id, Consumer<CreativeModeTab.Builder> builderConsumer) {
        CreativeModeTab.Builder builder = FabricItemGroup.builder(id);
        builderConsumer.accept(builder);
        return builder::build;
    }

    public static void addToTab(Supplier<CreativeModeTab> tab, Consumer<CreativeModeTabRegistry.ItemGroupEntries> entriesConsumer) {
        ItemGroupEvents.modifyEntriesEvent(tab.get()).register(entries -> entriesConsumer.accept(new ItemGroupEntriesWrapper(entries)));
    }

    @SuppressWarnings("UnstableApiUsage")
    private record ItemGroupEntriesWrapper(
            FabricItemGroupEntries entries) implements CreativeModeTabRegistry.ItemGroupEntries {

        @Override
        public void add(ItemLike... item) {
            for (ItemLike itemLike : item) {
                this.entries.accept(itemLike);
            }
        }

        @Override
        public void add(CreativeModeTab.TabVisibility visibility, ItemLike... item) {
            for (ItemLike itemLike : item) {
                this.entries.accept(itemLike, visibility);
            }
        }

        @Override
        public void addAfter(ItemLike afterLast, ItemLike... item) {
            this.entries.addAfter(afterLast, item);
        }

        @Override
        public void addAfter(ItemLike afterLast, CreativeModeTab.TabVisibility visibility, ItemLike... item) {
            Collection<ItemStack> list = Arrays.stream(item).map(i -> i.asItem().getDefaultInstance()).toList();
            this.entries.addAfter(afterLast, list, visibility);
        }

        @Override
        public void addBefore(ItemLike beforeFirst, ItemLike... item) {
            this.entries.addBefore(beforeFirst, item);
        }

        @Override
        public void addBefore(ItemLike beforeFirst, CreativeModeTab.TabVisibility visibility, ItemLike... item) {
            Collection<ItemStack> list = Arrays.stream(item).map(i -> i.asItem().getDefaultInstance()).toList();
            this.entries.addBefore(beforeFirst, list, visibility);
        }

        @Override
        public void add(ItemStack... stack) {
            for (ItemStack itemStack : stack) {
                this.entries.accept(itemStack);
            }
        }

        @Override
        public void add(CreativeModeTab.TabVisibility visibility, ItemStack... stack) {
            for (ItemStack itemStack : stack) {
                this.entries.accept(itemStack, visibility);
            }
        }

        @Override
        public void addAfter(ItemLike afterLast, ItemStack... item) {
            this.entries.addAfter(afterLast, item);
        }

        @Override
        public void addAfter(ItemLike afterLast, CreativeModeTab.TabVisibility visibility, ItemStack... item) {
            this.entries.addAfter(afterLast, Arrays.asList(item), visibility);
        }

        @Override
        public void addBefore(ItemLike beforeFirst, ItemStack... item) {
            this.entries.addBefore(beforeFirst, item);
        }

        @Override
        public void addBefore(ItemLike beforeFirst, CreativeModeTab.TabVisibility visibility, ItemStack... item) {
            this.entries.addBefore(beforeFirst, Arrays.asList(item), visibility);
        }
    }

}
