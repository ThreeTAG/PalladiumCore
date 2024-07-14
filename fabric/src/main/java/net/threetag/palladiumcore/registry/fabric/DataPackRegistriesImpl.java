package net.threetag.palladiumcore.registry.fabric;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.Nullable;

public class DataPackRegistriesImpl {

    public static <T> void create(ResourceKey<? extends Registry<T>> key, Codec<T> dataCodec, @Nullable Codec<T> networkCodec) {
        if (networkCodec == null) {
            DynamicRegistries.register(key, dataCodec);
        } else {
            DynamicRegistries.registerSynced(key, dataCodec, networkCodec);
        }
    }

}
