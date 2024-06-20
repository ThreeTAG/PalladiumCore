package net.threetag.palladiumcore.registry.client.neoforge;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.threetag.palladiumcore.PalladiumCore;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings({"rawtypes", "unchecked"})
@EventBusSubscriber(modid = PalladiumCore.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BlockEntityRendererRegistryImpl {

    private static final Map<Supplier<BlockEntityType<?>>, BlockEntityRendererProvider<?>> RENDERERS = new HashMap<>();

    public static <T extends BlockEntity> void register(Supplier<BlockEntityType<T>> type, BlockEntityRendererProvider<? super T> provider) {
        Supplier<BlockEntityType<?>> supplier = type::get;
        RENDERERS.put(supplier, provider);
    }

    @SubscribeEvent
    public static void renderers(EntityRenderersEvent.RegisterRenderers event) {
        for (Map.Entry<Supplier<BlockEntityType<?>>, BlockEntityRendererProvider<?>> entry : RENDERERS.entrySet()) {
            BlockEntityType type = entry.getKey().get();
            BlockEntityRendererProvider renderer = entry.getValue();
            event.registerBlockEntityRenderer(type, renderer);
        }
    }
}
