package net.threetag.palladiumcore.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.threetag.palladiumcore.PalladiumCoreClient;
import net.threetag.palladiumcore.event.LifecycleEvents;
import net.threetag.palladiumcore.network.fabric.SpawnEntityPacket;

public class PalladiumCoreClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        PalladiumCoreClient.init();
        SpawnEntityPacket.Client.register();
        LifecycleEvents.SETUP.invoker().run();
        LifecycleEvents.CLIENT_SETUP.invoker().run();
    }
}
