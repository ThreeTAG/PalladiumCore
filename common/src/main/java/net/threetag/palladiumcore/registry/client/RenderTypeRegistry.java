package net.threetag.palladiumcore.registry.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

@Environment(EnvType.CLIENT)
public class RenderTypeRegistry {

    @ExpectPlatform
    public static void registerBlock(RenderType type, Block... blocks) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerFluid(RenderType type, Fluid... fluids) {
        throw new AssertionError();
    }

}
