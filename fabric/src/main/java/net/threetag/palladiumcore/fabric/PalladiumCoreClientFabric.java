package net.threetag.palladiumcore.fabric;

import net.fabricmc.api.ModInitializer;
import net.threetag.palladiumcore.PalladiumCoreClient;
import net.threetag.palladiumcore.network.fabric.SpawnEntityPacket;

public class PalladiumCoreClientFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        PalladiumCoreClient.init();
        SpawnEntityPacket.Client.register();
    }
    
}
