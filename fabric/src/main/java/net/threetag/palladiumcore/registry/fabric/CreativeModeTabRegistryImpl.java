package net.threetag.palladiumcore.registry.fabric;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
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
        var tab = builder.build();
        return () -> tab;
    }

    public static void addToTab(Supplier<CreativeModeTab> tab, Consumer<CreativeModeTabRegistry.ItemGroupEntries> entriesConsumer) {
        ItemGroupEvents.modifyEntriesEvent(tab.get()).register(entries -> entriesConsumer.accept(new ItemGroupEntriesWrapper(entries)));
    }

    public static CreativeModeTab getTabByName(ResourceLocation id) {
        // some IDs differ between Forge & Fabric
        if(id.toString().equals("minecraft:natural_blocks")) {
            return CreativeModeTabs.NATURAL_BLOCKS;
        } else if(id.toString().equals("minecraft:functional_blocks")) {
            return CreativeModeTabs.FUNCTIONAL_BLOCKS;
        } else if(id.toString().equals("minecraft:redstone_blocks")) {
            return CreativeModeTabs.REDSTONE_BLOCKS;
        } else if(id.toString().equals("minecraft:tools_and_utilities")) {
            return CreativeModeTabs.TOOLS_AND_UTILITIES;
        } else if(id.toString().equals("minecraft:food_and_drinks")) {
            return CreativeModeTabs.FOOD_AND_DRINKS;
        }

        for (CreativeModeTab tab : CreativeModeTabs.allTabs()) {
            if (tab.getId().equals(id)) {
                return tab;
            }
        }
        return null;
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
