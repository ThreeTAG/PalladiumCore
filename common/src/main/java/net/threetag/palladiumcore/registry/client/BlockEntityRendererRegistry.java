package net.threetag.palladiumcore.registry.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class BlockEntityRendererRegistry {

    @ExpectPlatform
    public static <T extends BlockEntity> void register(Supplier<BlockEntityType<T>> type, BlockEntityRendererProvider<? super T> provider) {
        throw new AssertionError();
    }

}
