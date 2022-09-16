package net.threetag.palladiumcore.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
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

        ClientTickEvents.START_CLIENT_TICK.register(instance -> net.threetag.palladiumcore.event.ClientTickEvents.CLIENT_PRE.invoker().clientTick(instance));
        ClientTickEvents.END_CLIENT_TICK.register(instance -> net.threetag.palladiumcore.event.ClientTickEvents.CLIENT_POST.invoker().clientTick(instance));
        ClientTickEvents.START_WORLD_TICK.register(instance -> net.threetag.palladiumcore.event.ClientTickEvents.CLIENT_LEVEL_PRE.invoker().clientLevelTick(Minecraft.getInstance(), instance));
        ClientTickEvents.END_WORLD_TICK.register(instance -> net.threetag.palladiumcore.event.ClientTickEvents.CLIENT_LEVEL_POST.invoker().clientLevelTick(Minecraft.getInstance(), instance));
    }
}
