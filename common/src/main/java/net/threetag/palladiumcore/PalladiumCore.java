package net.threetag.palladiumcore;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.threetag.palladiumcore.item.PalladiumSpawnEggItem;
import net.threetag.palladiumcore.registry.CreativeModeTabRegistry;
import org.slf4j.Logger;

public class PalladiumCore {

    public static final String MOD_ID = "palladiumcore";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        PalladiumSpawnEggItem.setupEvents();

        CreativeModeTabRegistry.create(id("test"), builder -> {
            builder.icon(Items.ACACIA_BOAT::getDefaultInstance);
            builder.displayItems((featureFlagSet, output, bl) -> {
                output.accept(Items.ACACIA_BOAT);
                output.accept(Items.AZALEA_LEAVES);
            });
        });

        CreativeModeTabRegistry.addToTab(CreativeModeTabs.COMBAT, entries -> {
            entries.add(Items.GLOW_ITEM_FRAME);
            entries.addAfter(Items.IRON_SWORD, Items.ANDESITE_STAIRS, Items.ANDESITE_SLAB);
            entries.addBefore(Items.WOODEN_SWORD, Items.GRANITE_STAIRS, Items.GRANITE_SLAB);
        });
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
