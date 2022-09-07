package net.threetag.palladiumcore.fabric;

import net.fabricmc.api.ModInitializer;
import net.threetag.palladiumcore.PalladiumCoreClient;

public class PalladiumCoreClientFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        PalladiumCoreClient.init();
    }
    
}
