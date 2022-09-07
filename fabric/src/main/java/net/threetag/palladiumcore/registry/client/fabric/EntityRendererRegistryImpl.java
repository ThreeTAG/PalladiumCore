package net.threetag.palladiumcore.registry.client.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class EntityRendererRegistryImpl {

    public static <T extends Entity> void register(Supplier<? extends EntityType<? extends T>> typeSupplier, EntityRendererProvider<T> provider) {
        EntityRendererRegistry.register(typeSupplier.get(), provider);
    }

    public static void registerModelLayer(ModelLayerLocation location, Supplier<LayerDefinition> definitionSupplier) {
        EntityModelLayerRegistry.registerModelLayer(location, definitionSupplier::get);
    }

}
