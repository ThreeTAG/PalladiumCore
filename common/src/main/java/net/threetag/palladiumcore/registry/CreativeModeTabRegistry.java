package net.threetag.palladiumcore.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CreativeModeTabRegistry {

    /**
     * Creates a {@link CreativeModeTab}
     *
     * @param id              ID for the tab, the translation key will consist out of "namespace"."path"
     * @param builderConsumer {@link Consumer} which allows for modifications on the tab builder
     * @return A new {@link Supplier} which will return a new {@link CreativeModeTab}
     */
    @ExpectPlatform
    public static Supplier<CreativeModeTab> create(ResourceLocation id, Consumer<CreativeModeTab.Builder> builderConsumer) {
        throw new AssertionError();
    }

    /**
     * Allows to add items to existing creative mode tabs
     *
     * @param tab             Supplier which returns the tab you want to add to
     * @param entriesConsumer {@link Consumer} which allows for modifications to the tab
     */
    @ExpectPlatform
    public static void addToTab(Supplier<CreativeModeTab> tab, Consumer<ItemGroupEntries> entriesConsumer) {
        throw new AssertionError();
    }

    /**
     * Allows to add items to existing creative mode tabs
     *
     * @param tab             The tab you want to add to
     * @param entriesConsumer {@link Consumer} which allows for modifications to the tab
     */
    public static void addToTab(CreativeModeTab tab, Consumer<ItemGroupEntries> entriesConsumer) {
        addToTab(() -> tab, entriesConsumer);
    }

    /**
     * @param id ID of the tab that is searched for
     * @return The wanted {@link CreativeModeTab}, or null if not found
     */
    @ExpectPlatform
    @Nullable
    public static CreativeModeTab getTabByName(ResourceLocation id) {
        throw new AssertionError();
    }

    public interface ItemGroupEntries {

        void add(ItemLike... item);

        void add(CreativeModeTab.TabVisibility visibility, ItemLike... item);

        void addAfter(ItemLike afterLast, ItemLike... item);

        void addAfter(ItemLike afterLast, CreativeModeTab.TabVisibility visibility, ItemLike... item);

        void addBefore(ItemLike beforeFirst, ItemLike... item);

        void addBefore(ItemLike beforeFirst, CreativeModeTab.TabVisibility visibility, ItemLike... item);

        void add(ItemStack... stack);

        void add(CreativeModeTab.TabVisibility visibility, ItemStack... stack);

        void addAfter(ItemLike afterLast, ItemStack... item);

        void addAfter(ItemLike afterLast, CreativeModeTab.TabVisibility visibility, ItemStack... item);

        void addBefore(ItemLike beforeFirst, ItemStack... item);

        void addBefore(ItemLike beforeFirst, CreativeModeTab.TabVisibility visibility, ItemStack... item);

    }

}
