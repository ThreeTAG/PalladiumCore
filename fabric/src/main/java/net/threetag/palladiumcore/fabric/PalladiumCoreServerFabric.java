package net.threetag.palladiumcore.fabric;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.threetag.palladiumcore.event.LifecycleEvents;

public class PalladiumCoreServerFabric implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> LifecycleEvents.SETUP.invoker().run());
    }
}
