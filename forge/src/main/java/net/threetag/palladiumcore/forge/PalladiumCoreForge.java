package net.threetag.palladiumcore.forge;

import net.minecraftforge.fml.common.Mod;
import net.threetag.palladiumcore.PalladiumCore;
import net.threetag.palladiumcore.PalladiumCoreClient;
import net.threetag.palladiumcore.util.Platform;

@Mod(PalladiumCore.MOD_ID)
public class PalladiumCoreForge {

    public PalladiumCoreForge() {
        PalladiumCore.init();

        if (Platform.isClient()) {
            PalladiumCoreClient.init();
        }
    }
}
