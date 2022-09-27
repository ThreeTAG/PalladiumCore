package net.threetag.palladiumcore.registry.client.fabric;

import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class BlockEntityRendererRegistryImpl {

    public static <T extends BlockEntity> void register(Supplier<BlockEntityType<T>> type, BlockEntityRendererProvider<? super T> provider) {
        BlockEntityRendererRegistry.register(type.get(), provider);
    }
}
