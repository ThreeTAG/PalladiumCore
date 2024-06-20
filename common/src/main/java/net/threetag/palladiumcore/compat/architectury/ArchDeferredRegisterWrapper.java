package net.threetag.palladiumcore.compat.architectury;

import com.mojang.datafixers.util.Either;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.threetag.palladiumcore.registry.DeferredRegister;
import net.threetag.palladiumcore.registry.RegistryHolder;
import net.threetag.palladiumcore.util.Platform;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ArchDeferredRegisterWrapper<T> extends DeferredRegister<T> {

    private final dev.architectury.registry.registries.DeferredRegister<T> register;
    private final ResourceKey<Registry<T>> resourceKey;
    private final Map<dev.architectury.registry.registries.RegistrySupplier<T>, RegistryHolder<T, ? extends T>> suppliers = new HashMap<>();

    public ArchDeferredRegisterWrapper(String modid, ResourceKey<Registry<T>> resourceKey) {
        this.register = dev.architectury.registry.registries.DeferredRegister.create(modid, resourceKey);
        this.resourceKey = resourceKey;
    }

    public static <T> DeferredRegister<T> get(String modid, ResourceKey<Registry<T>> resourceKey) {
        return new ArchDeferredRegisterWrapper<>(modid, resourceKey);
    }

    @Override
    public void register() {
        this.register.register();
    }

    @Override
    public <R extends T> RegistryHolder<T, R> register(String id, Supplier<R> supplier) {
        var obj = this.register.register(id, supplier);
        var registrySupplier = (RegistryHolder<T, R>) this.suppliers.computeIfAbsent((RegistrySupplier<T>) obj, RegistryHolderImpl::new);
        if (Platform.isFabric() && this.resourceKey.equals(Registries.POINT_OF_INTEREST_TYPE)) {
            POI_TYPES_TO_FIX.add((RegistryHolder) registrySupplier);
        }
        return registrySupplier;
    }

    @Override
    public Collection<RegistryHolder<T, ? extends T>> getEntries() {
        return this.suppliers.values();
    }

    public static class RegistryHolderImpl<R, T extends R> extends RegistryHolder<R, T> {

        private final RegistrySupplier<R> registrySupplier;

        public RegistryHolderImpl(RegistrySupplier<R> registrySupplier) {
            this.registrySupplier = registrySupplier;
        }

        @Override
        public ResourceLocation getId() {
            return this.registrySupplier.getRegistryId();
        }

        @Override
        public T get() {
            return (T) this.registrySupplier.get();
        }

        @Override
        public @NotNull R value() {
            return this.registrySupplier.get();
        }

        @Override
        public boolean isBound() {
            return this.registrySupplier.isBound();
        }

        @Override
        public boolean is(ResourceLocation location) {
            return this.registrySupplier.is(location);
        }

        @Override
        public boolean is(ResourceKey<R> resourceKey) {
            return this.registrySupplier.is(resourceKey);
        }

        @Override
        public boolean is(Predicate<ResourceKey<R>> predicate) {
            return this.registrySupplier.is(predicate);
        }

        @Override
        public boolean is(TagKey<R> tagKey) {
            return this.registrySupplier.is(tagKey);
        }

        @Override
        public boolean is(Holder<R> holder) {
            return this.registrySupplier.is(holder);
        }

        @Override
        public @NotNull Stream<TagKey<R>> tags() {
            return this.registrySupplier.tags();
        }

        @Override
        public @NotNull Either<ResourceKey<R>, R> unwrap() {
            return this.registrySupplier.unwrap();
        }

        @Override
        public @NotNull Optional<ResourceKey<R>> unwrapKey() {
            return this.registrySupplier.unwrapKey();
        }

        @Override
        public @NotNull Kind kind() {
            return this.registrySupplier.kind();
        }

        @Override
        public boolean canSerializeIn(HolderOwner<R> owner) {
            return this.registrySupplier.canSerializeIn(owner);
        }

    }

}
