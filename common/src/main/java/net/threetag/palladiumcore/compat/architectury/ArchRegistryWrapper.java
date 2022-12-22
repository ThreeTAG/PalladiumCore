package net.threetag.palladiumcore.compat.architectury;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.threetag.palladiumcore.registry.PalladiumRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.*;

public class ArchRegistryWrapper<T> extends PalladiumRegistry<T> {

    private final Registrar<T> registrar;

    @SuppressWarnings("unchecked")
    public ArchRegistryWrapper(ResourceLocation id, Class<T> clazz) {
        T[] a = (T[]) Array.newInstance(clazz, 0);
        this.registrar = Registries.get(id.getNamespace()).builder(id, a).build();
    }

    public static <T> PalladiumRegistry<T> get(ResourceLocation id, Class<T> clazz) {
        return new ArchRegistryWrapper<>(id, clazz);
    }

    @Override
    public ResourceKey<? extends Registry<T>> getRegistryKey() {
        return this.registrar.key();
    }

    @Override
    public @Nullable T get(ResourceLocation key) {
        return this.registrar.get(key);
    }

    @Override
    public @Nullable ResourceLocation getKey(T object) {
        return this.registrar.getId(object);
    }

    @Override
    public boolean containsKey(ResourceLocation key) {
        return this.registrar.contains(key);
    }

    @Override
    public Set<ResourceLocation> getKeys() {
        return this.registrar.getIds();
    }

    @Override
    public Collection<T> getValues() {
        List<T> list = new ArrayList<>();
        this.registrar.forEach(list::add);
        return list;
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return this.registrar.iterator();
    }
}
