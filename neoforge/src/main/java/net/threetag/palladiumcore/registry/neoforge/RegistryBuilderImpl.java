package net.threetag.palladiumcore.registry.neoforge;

import net.minecraft.core.Registry;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class RegistryBuilderImpl {

    private static <T> Registry<T> createRegistry(net.threetag.palladiumcore.registry.RegistryBuilder<T> registryBuilder) {
        RegistryBuilder<T> neoBuilder = new RegistryBuilder<>(registryBuilder.getResourceKey()).sync(registryBuilder.isSynced());

        if (registryBuilder.getDefaultKey() != null) {
            neoBuilder.defaultKey(registryBuilder.getDefaultKey());
        }

        return neoBuilder.create();
    }

}
