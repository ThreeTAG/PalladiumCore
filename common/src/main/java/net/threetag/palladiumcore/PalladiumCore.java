package net.threetag.palladiumcore;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.threetag.palladiumcore.event.LifecycleEvents;
import net.threetag.palladiumcore.item.PalladiumRecordItem;
import net.threetag.palladiumcore.item.PalladiumSpawnEggItem;
import net.threetag.palladiumcore.util.DataSyncUtil;
import org.slf4j.Logger;

public class PalladiumCore {

    public static final String MOD_ID = "palladiumcore";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        DataSyncUtil.setupEvents();
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
