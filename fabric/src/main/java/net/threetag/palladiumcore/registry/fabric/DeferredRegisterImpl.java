package net.threetag.palladiumcore.registry.fabric;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.threetag.palladiumcore.registry.DeferredRegister;
import net.threetag.palladiumcore.registry.RegistrySupplier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class DeferredRegisterImpl {

    public static <T> DeferredRegister<T> createInternal(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
        return new Impl<>(modid, resourceKey);
    }

    @SuppressWarnings({"unchecked", "ConstantConditions", "rawtypes"})
    public static class Impl<T> extends DeferredRegister<T> {

        private final String modid;
        private final Registry<T> registry;
        private final List<RegistrySupplier<T>> entries;

        public Impl(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
            this.modid = modid;
            this.registry = (Registry<T>) BuiltInRegistries.REGISTRY.get(resourceKey.location());
            this.entries = new ArrayList<>();
        }

        @Override
        public void register() {

        }

        @Override
        public <R extends T> RegistrySupplier<R> register(String id, Supplier<R> supplier) {
            ResourceLocation registeredId = new ResourceLocation(this.modid, id);
            RegistrySupplier<R> registrySupplier = new RegistrySupplier<>(registeredId, Registry.register(this.registry, registeredId, supplier.get()));
            this.entries.add((RegistrySupplier<T>) registrySupplier);
            if(this.registry == BuiltInRegistries.POINT_OF_INTEREST_TYPE) {
                POI_TYPES_TO_FIX.add((RegistrySupplier) registrySupplier);
            }
            return registrySupplier;
        }

        @Override
        public Collection<RegistrySupplier<T>> getEntries() {
            return ImmutableList.copyOf(this.entries);
        }
    }

}
