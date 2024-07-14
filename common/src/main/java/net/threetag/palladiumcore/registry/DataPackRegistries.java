package net.threetag.palladiumcore.registry;

import com.mojang.serialization.Codec;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.Nullable;

/**
 * Utility class to create custom datapack registries.
 * MUST be initialized during mod initialized so that it's registered early enough for Forge's registry event.
 * <p>
 * Data JSONs will be loaded from {@code data/<datapack_namespace>/modid/registryname/}, where {@code modid} is the namespace of the registry key.
 */
public class DataPackRegistries {

    /**
     * Registers the registry key as a datapack registry, which will cause data to be loaded from
     * a datapack folder based on the registry's name.
     * <p>
     * Data JSONs will be loaded from {@code data/<datapack_namespace>/modid/registryname/}, where {@code modid} is the namespace of the registry key.
     *
     * @param key          The root registry key of the new datapack registry
     * @param dataCodec    The codec to be used for loading data from datapacks on servers
     * @param networkCodec The codec to be used for syncing loaded data to clients.
     *                     If {@code networkCodec} is null, data will not be synced, and clients are not required to have this
     *                     datapack registry to join a server.
     *                     <p>
     *                     If {@code networkCodec} is not null, clients must have this datapack registry/mod
     *                     when joining a server that has this datapack registry/mod.
     */
    @ExpectPlatform
    public static <T> void create(ResourceKey<? extends Registry<T>> key, Codec<T> dataCodec, @Nullable Codec<T> networkCodec) {
        throw new AssertionError();
    }

}
