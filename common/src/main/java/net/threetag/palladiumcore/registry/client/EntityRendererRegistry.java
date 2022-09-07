package net.threetag.palladiumcore.registry.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class EntityRendererRegistry {

    @ExpectPlatform
    public static <T extends Entity> void register(Supplier<? extends EntityType<? extends T>> typeSupplier, EntityRendererProvider<T> provider) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerModelLayer(ModelLayerLocation location, Supplier<LayerDefinition> definitionSupplier) {
        throw new AssertionError();
    }

}
