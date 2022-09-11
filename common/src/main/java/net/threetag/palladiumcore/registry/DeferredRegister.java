package net.threetag.palladiumcore.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.Collection;
import java.util.function.Supplier;

public abstract class DeferredRegister<T> {

    public abstract void register();

    public abstract <R extends T> RegistrySupplier<R> register(String id, Supplier<R> supplier);

    public abstract Collection<RegistrySupplier<T>> getEntries();

    @ExpectPlatform
    public static <T> DeferredRegister<T> create(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
        throw new AssertionError();
    }

    public static <T> DeferredRegister<T> create(String modid, PalladiumRegistry<T> registry) {
        return create(modid, registry.getRegistryKey());
    }

}
