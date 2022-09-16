package net.threetag.palladiumcore.registry.client.fabric;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class RenderTypeRegistryImpl {

    public static void registerBlock(RenderType type, Block... blocks) {
        BlockRenderLayerMap.INSTANCE.putBlocks(type, blocks);
    }

    public static void registerFluid(RenderType type, Fluid... fluids) {
        BlockRenderLayerMap.INSTANCE.putFluids(type, fluids);
    }
}
