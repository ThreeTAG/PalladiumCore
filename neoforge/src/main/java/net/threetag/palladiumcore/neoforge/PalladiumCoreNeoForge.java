package net.threetag.palladiumcore.neoforge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.threetag.palladiumcore.PalladiumCore;
import net.threetag.palladiumcore.PalladiumCoreClient;
import net.threetag.palladiumcore.event.LifecycleEvents;
import net.threetag.palladiumcore.util.Platform;

import java.util.Optional;
import java.util.function.Consumer;

@Mod(PalladiumCore.MOD_ID)
@EventBusSubscriber(modid = PalladiumCore.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class PalladiumCoreNeoForge {

    public PalladiumCoreNeoForge() {
        PalladiumCore.init();

        if (Platform.isClient()) {
            PalladiumCoreClient.init();
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void event(FMLCommonSetupEvent event) {
        LifecycleEvents.SETUP.invoker().run();
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void event(FMLClientSetupEvent event) {
        event.enqueueWork(() -> LifecycleEvents.CLIENT_SETUP.invoker().run());
    }

    public static Optional<IEventBus> getModEventBus(String modId) {
        return ModList.get().getModContainerById(modId)
                .map(ModContainer::getEventBus);
    }

    public static void whenModBusAvailable(String modId, Consumer<IEventBus> busConsumer) {
        IEventBus bus = getModEventBus(modId).orElseThrow(() -> new IllegalStateException("Mod '" + modId + "' is not available!"));
        busConsumer.accept(bus);
    }

}
