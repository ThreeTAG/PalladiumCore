package net.threetag.palladiumcore.fabric;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.threetag.palladiumcore.event.LifecycleEvents;

public class PalladiumCoreServerFabric implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {
        LifecycleEvents.SETUP.invoker().run();
    }
}
