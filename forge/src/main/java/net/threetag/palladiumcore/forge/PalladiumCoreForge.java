package net.threetag.palladiumcore.forge;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.threetag.palladiumcore.PalladiumCore;
import net.threetag.palladiumcore.PalladiumCoreClient;
import net.threetag.palladiumcore.compat.architectury.forge.ArchitecturyCompatForge;
import net.threetag.palladiumcore.event.LifecycleEvents;
import net.threetag.palladiumcore.util.Platform;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mod(PalladiumCore.MOD_ID)
@Mod.EventBusSubscriber(modid = PalladiumCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PalladiumCoreForge {

    private static final Map<String, IEventBus> EVENT_BUSSES = Collections.synchronizedMap(new HashMap<>());

    public PalladiumCoreForge() {
        registerModEventBus(PalladiumCore.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

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

    public static void registerModEventBus(String modId, IEventBus bus) {
        if (EVENT_BUSSES.putIfAbsent(modId, bus) != null) {
            throw new IllegalStateException("Event bus for mod '" + modId + "' was already registered!");
        }

        if (Platform.isModLoaded("architectury")) {
            ArchitecturyCompatForge.registerModEventBus(modId, bus);
        }
    }

    public static Optional<IEventBus> getModEventBus(String modId) {
        return Optional.ofNullable(EVENT_BUSSES.get(modId));
    }

}
