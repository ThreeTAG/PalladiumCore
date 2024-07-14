package net.threetag.palladiumcore.registry.neoforge;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.EventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.threetag.palladiumcore.PalladiumCore;
import net.threetag.palladiumcore.neoforge.PalladiumCoreNeoForge;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = PalladiumCore.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataPackRegistriesImpl {

    public static <T> void create(ResourceKey<Registry<T>> key, Codec<T> dataCodec, Codec<T> networkCodec) {
        PalladiumCoreNeoForge.whenModBusAvailable(key.location().getNamespace(), bus -> {
            bus.<DataPackRegistryEvent.NewRegistry>addListener(event -> event.dataPackRegistry(key, dataCodec, networkCodec));
        });
    }

}
