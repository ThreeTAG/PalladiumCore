package net.threetag.palladiumcore.registry.client.forge;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.threetag.palladiumcore.PalladiumCore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
@Mod.EventBusSubscriber(modid = PalladiumCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EntityRendererRegistryImpl {

    private static final Map<Supplier<EntityType<?>>, EntityRendererProvider<?>> RENDERERS = new HashMap<>();
    private static final Map<ModelLayerLocation, Supplier<LayerDefinition>> MODEL_LAYERS = new ConcurrentHashMap<>();

    public static <T extends Entity> void register(Supplier<? extends EntityType<? extends T>> type, EntityRendererProvider<T> factory) {
        RENDERERS.put((Supplier<EntityType<?>>) (Supplier<? extends EntityType<?>>) type, factory);
    }

    public static void registerModelLayer(ModelLayerLocation location, Supplier<LayerDefinition> definitionSupplier) {
        MODEL_LAYERS.put(location, definitionSupplier);
    }

    @SubscribeEvent
    public static void event(EntityRenderersEvent.RegisterRenderers event) {
        for (Map.Entry<Supplier<EntityType<?>>, EntityRendererProvider<?>> entry : RENDERERS.entrySet()) {
            event.registerEntityRenderer(entry.getKey().get(), (EntityRendererProvider<Entity>) entry.getValue());
        }
    }

    @SubscribeEvent
    public static void event(EntityRenderersEvent.RegisterLayerDefinitions event) {
        for (Map.Entry<ModelLayerLocation, Supplier<LayerDefinition>> entry : MODEL_LAYERS.entrySet()) {
            event.registerLayerDefinition(entry.getKey(), entry.getValue());
        }
    }

}
