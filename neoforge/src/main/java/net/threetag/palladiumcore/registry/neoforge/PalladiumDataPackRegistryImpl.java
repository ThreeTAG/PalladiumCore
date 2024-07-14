package net.threetag.palladiumcore.registry.neoforge;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.threetag.palladiumcore.PalladiumCore;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = PalladiumCore.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class PalladiumDataPackRegistryImpl {

    private static final List<RegistryInfo<?>> REGISTRIES = new ArrayList<>();

    public static <T> void create(ResourceKey<? extends Registry<T>> key, Codec<T> dataCodec, Codec<T> networkCodec) {
        REGISTRIES.add(new RegistryInfo<>(key, dataCodec, networkCodec));
    }

    @SubscribeEvent
    public static void registerDataPackRegistries(DataPackRegistryEvent.NewRegistry e) {
        for (RegistryInfo<?> registry : REGISTRIES) {
            registry.registerDataPackRegistries(e);
        }
    }

    private record RegistryInfo<T>(ResourceKey<? extends Registry<T>> key, Codec<T> dataCodec, Codec<T> networkCodec) {

        @SuppressWarnings({"rawtypes", "unchecked"})
        public void registerDataPackRegistries(DataPackRegistryEvent.NewRegistry e) {
            ResourceKey key = this.key();
            e.dataPackRegistry(key, this.dataCodec(), this.networkCodec());
        }

    }

}
