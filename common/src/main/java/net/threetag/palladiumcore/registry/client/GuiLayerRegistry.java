package net.threetag.palladiumcore.registry.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class GuiLayerRegistry {

    @ExpectPlatform
    public static void register(ResourceLocation id, LayeredDraw.Layer layer) {
        throw new AssertionError();
    }

}
