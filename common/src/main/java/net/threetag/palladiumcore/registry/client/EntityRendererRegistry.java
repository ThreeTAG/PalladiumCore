package net.threetag.palladiumcore.registry.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.function.Function;
import java.util.function.Predicate;
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

    @ExpectPlatform
    public static void addRenderLayer(Predicate<EntityType<?>> entityType, Function<RenderLayerParent<?, ?>, RenderLayer<?, ?>> renderLayer) {
        throw new AssertionError();
    }

    public static void addRenderLayer(Supplier<EntityType<?>> entityType, Function<RenderLayerParent<?, ?>, RenderLayer<?, ?>> renderLayer) {
        addRenderLayer(type -> type == entityType.get(), renderLayer);
    }

    public static void addRenderLayerToPlayer(Function<RenderLayerParent<?, ?>, RenderLayer<?, ?>> renderLayer) {
        addRenderLayer(type -> type == EntityType.PLAYER, renderLayer);
    }

    public static void addRenderLayerToAll(Function<RenderLayerParent<?, ?>, RenderLayer<?, ?>> renderLayer) {
        addRenderLayer(type -> true, renderLayer);
    }

}
