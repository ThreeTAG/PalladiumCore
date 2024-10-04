package net.threetag.palladiumcore.registry.neoforge;

import net.minecraft.core.Registry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import net.threetag.palladiumcore.PalladiumCore;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = PalladiumCore.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class RegistryBuilderImpl {

    private static final List<Registry<?>> REGISTRIES = new ArrayList<>();

    public static <T> Registry<T> createRegistry(net.threetag.palladiumcore.registry.RegistryBuilder<T> registryBuilder) {
        RegistryBuilder<T> neoBuilder = new RegistryBuilder<>(registryBuilder.getResourceKey()).sync(registryBuilder.isSynced());

        if (registryBuilder.getDefaultKey() != null) {
            neoBuilder.defaultKey(registryBuilder.getDefaultKey());
        }

        return neoBuilder.create();
    }

    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event) {
        for (Registry<?> registry : REGISTRIES) {
            event.register(registry);
        }
    }

}
