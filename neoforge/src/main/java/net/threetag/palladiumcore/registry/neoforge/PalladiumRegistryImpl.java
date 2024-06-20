package net.threetag.palladiumcore.registry.neoforge;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;
import net.threetag.palladiumcore.neoforge.PalladiumCoreNeoForge;
import net.threetag.palladiumcore.registry.PalladiumRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class PalladiumRegistryImpl<T> extends PalladiumRegistry<T> {

    public static <T> PalladiumRegistry<T> createInternal(ResourceKey<Registry<T>> resourceKey) {
        DeferredRegister<T> deferredRegister = DeferredRegister.create(resourceKey, resourceKey.location().getNamespace());
        var registry = deferredRegister.makeRegistry(RegistryBuilder::create);
        deferredRegister.register(PalladiumCoreNeoForge.getModEventBus(resourceKey.location().getNamespace()).orElseThrow(() -> new IllegalStateException("Mod '" + resourceKey.location().getNamespace() + "' did not register event bus to PalladiumCore!")));
        return new PalladiumRegistryImpl<>(resourceKey, registry);
    }

    private final Registry<T> parent;
    private final ResourceKey<Registry<T>> resourceKey;

    public PalladiumRegistryImpl(ResourceKey<Registry<T>> resourceKey, Registry<T> parent) {
        this.parent = parent;
        this.resourceKey = resourceKey;
    }

    @Override
    public ResourceKey<Registry<T>> getRegistryKey() {
        return this.resourceKey;
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
        return this.parent.stream().toList();
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return this.parent.iterator();
    }
}
