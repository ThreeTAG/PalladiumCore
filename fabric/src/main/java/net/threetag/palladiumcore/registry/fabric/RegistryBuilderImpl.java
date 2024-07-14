package net.threetag.palladiumcore.registry.fabric;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.Registry;
import net.threetag.palladiumcore.registry.RegistryBuilder;

public class RegistryBuilderImpl {

    private static <T> Registry<T> createRegistry(RegistryBuilder<T> registryBuilder) {
        var builder = registryBuilder.getDefaultKey() != null ? FabricRegistryBuilder.createDefaulted(registryBuilder.getResourceKey(), registryBuilder.getDefaultKey()) : FabricRegistryBuilder.createSimple(registryBuilder.getResourceKey());

        if (registryBuilder.isSynced()) {
            builder.attribute(RegistryAttribute.SYNCED);
        }

        return builder.buildAndRegister();
    }

}
