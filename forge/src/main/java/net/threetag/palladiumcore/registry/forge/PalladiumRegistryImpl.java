package net.threetag.palladiumcore.registry.forge;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.threetag.palladiumcore.registry.PalladiumRegistry;

import java.util.Collection;
import java.util.Set;
import java.util.function.Supplier;

public class PalladiumRegistryImpl<T> extends PalladiumRegistry<T> {

    public static <T> PalladiumRegistry<T> create(Class<T> clazz, ResourceLocation id) {
        DeferredRegister<T> deferredRegister = DeferredRegister.create(id, id.getNamespace());
        deferredRegister.register(FMLJavaModLoadingContext.get().getModEventBus());
        return new PalladiumRegistryImpl<>(deferredRegister.makeRegistry(RegistryBuilder::new));
    }

    private final Supplier<IForgeRegistry<T>> parent;

    public PalladiumRegistryImpl(Supplier<IForgeRegistry<T>> parent) {
        this.parent = parent;
    }

    @Override
    public ResourceKey<? extends Registry<T>> getRegistryKey() {
        return this.parent.get().getRegistryKey();
    }

    @Override
    public T get(ResourceLocation key) {
        return this.parent.get().getValue(key);
    }

    @Override
    public ResourceLocation getKey(T object) {
        return this.parent.get().getKey(object);
    }

    @Override
    public Set<ResourceLocation> getKeys() {
        return this.parent.get().getKeys();
    }

    @Override
    public Collection<T> getValues() {
        return this.parent.get().getValues();
    }
}
