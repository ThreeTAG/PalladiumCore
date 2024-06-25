package net.threetag.palladiumcore.registry.client.forge;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.threetag.palladiumcore.PalladiumCore;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings({"rawtypes", "unchecked"})
@Mod.EventBusSubscriber(modid = PalladiumCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
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
