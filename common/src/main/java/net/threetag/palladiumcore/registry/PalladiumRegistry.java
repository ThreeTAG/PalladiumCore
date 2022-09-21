package net.threetag.palladiumcore.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Utility class to create custom registries.
 * MUST be initialized during mod initialized so that it's registered early enough for Forge's registry event.
 * <p>
 * Example Usage:
 * <pre>{@code
 *   public static final PalladiumRegistry<CreativeModeTab> REGISTRY = PalladiumRegistry.create(CreativeModeTab.class, new ResourceLocation("minecraft:creative_mode_tabs"));
 * }</pre>
 *
 * @param <T> The base registry type
 */
public abstract class PalladiumRegistry<T> implements Iterable<T> {

    /**
     * Creates a new {@link PalladiumRegistry} with the given class type and the registry id
     *
     * @param clazz Class of type of the registry
     * @param id    ID for the registry
     * @param <T>   Generic type of the registry
     * @return A new {@link PalladiumRegistry} instance
     */
    @ExpectPlatform
    public static <T> PalladiumRegistry<T> create(Class<T> clazz, ResourceLocation id) {
        throw new AssertionError();
    }

    /**
     * @return {@link ResourceKey} associated with the registry
     */
    public abstract ResourceKey<? extends Registry<T>> getRegistryKey();

    /**
     * Gets the object from the registry with the given key. Might be null if no such object has been registered
     *
     * @param key ID/Key of the wanted registered object
     * @return Either the wanted object or null
     */
    @Nullable
    public abstract T get(ResourceLocation key);

    /**
     * Gets the key of the given object. Might be null if the object has never been registered
     *
     * @param object The object which is used to find a key for
     * @return Either a {@link ResourceLocation} or null
     */
    @Nullable
    public abstract ResourceLocation getKey(T object);

    /**
     * @param key Key of a potentially registered object
     * @return True if something was registerd with that key
     */
    public abstract boolean containsKey(ResourceLocation key);

    /**
     * @return All keys registered in this registry
     */
    public abstract Set<ResourceLocation> getKeys();

    /**
     * @return All values registered in this registry
     */
    public abstract Collection<T> getValues();

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return this.getValues().iterator();
    }
}
