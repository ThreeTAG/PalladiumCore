package net.threetag.palladiumcore.forge;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.threetag.palladiumcore.PalladiumCore;
import net.threetag.palladiumcore.PalladiumCoreClient;
import net.threetag.palladiumcore.event.LifecycleEvents;
import net.threetag.palladiumcore.util.Platform;

@Mod(PalladiumCore.MOD_ID)
@Mod.EventBusSubscriber(modid = PalladiumCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PalladiumCoreForge {

    public PalladiumCoreForge() {
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
        LifecycleEvents.CLIENT_SETUP.invoker().run();
    }

}
