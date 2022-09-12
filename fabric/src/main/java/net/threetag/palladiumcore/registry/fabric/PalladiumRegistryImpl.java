package net.threetag.palladiumcore.registry.fabric;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.threetag.palladiumcore.registry.PalladiumRegistry;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PalladiumRegistryImpl<T> extends PalladiumRegistry<T> {

    public static <T> PalladiumRegistry<T> create(Class<T> clazz, ResourceLocation id) {
        return new PalladiumRegistryImpl<>(FabricRegistryBuilder.createSimple(clazz, id).buildAndRegister());
    }

    private final MappedRegistry<T> parent;

    public PalladiumRegistryImpl(MappedRegistry<T> parent) {
        this.parent = parent;
    }

    @Override
    public ResourceKey<? extends Registry<T>> getRegistryKey() {
        return this.parent.key();
    }

    @Override
    public T get(ResourceLocation key) {
        return this.parent.get(key);
    }

    @Override
    public ResourceLocation getKey(T object) {
        return this.parent.getKey(object);
    }

    @Override
    public boolean containsKey(ResourceLocation key) {
        return this.parent.containsKey(key);
    }

    @Override
    public Set<ResourceLocation> getKeys() {
        return this.parent.keySet();
    }

    @Override
    public Collection<T> getValues() {
        return this.parent.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }
}
