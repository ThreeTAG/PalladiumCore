package net.threetag.palladiumcore;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.threetag.palladiumcore.event.EventResult;
import net.threetag.palladiumcore.event.PlayerEvents;
import net.threetag.palladiumcore.item.PalladiumSpawnEggItem;
import net.threetag.palladiumcore.util.DataSyncUtil;
import org.slf4j.Logger;

public class PalladiumCore {

    public static final String MOD_ID = "palladiumcore";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        PalladiumSpawnEggItem.setupEvents();
        DataSyncUtil.setupEvents();

        PlayerEvents.ANVIL_UPDATE.register((player, left, right, name, cost, materialCost, output) -> {
            if (left.getItem() instanceof ArmorItem && right.getItem() == Items.COPPER_INGOT) {
                ItemStack copiedStack = Items.SPYGLASS.getDefaultInstance();
                cost.set(5);
                output.set(copiedStack);
            }

            return EventResult.pass();
        });
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
