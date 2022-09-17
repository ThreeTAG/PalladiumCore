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

public abstract class PalladiumRegistry<T> implements Iterable<T> {

    @ExpectPlatform
    public static <T> PalladiumRegistry<T> create(Class<T> clazz, ResourceLocation id) {
        throw new AssertionError();
    }

    public abstract ResourceKey<? extends Registry<T>> getRegistryKey();

    @Nullable
    public abstract T get(ResourceLocation key);

    @Nullable
    public abstract ResourceLocation getKey(T object);

    public abstract boolean containsKey(ResourceLocation key);

    public abstract Set<ResourceLocation> getKeys();

    public abstract Collection<T> getValues();

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return this.getValues().iterator();
    }
}
