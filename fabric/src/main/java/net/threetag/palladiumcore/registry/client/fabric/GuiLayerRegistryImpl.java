package net.threetag.palladiumcore.registry.client.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class GuiLayerRegistryImpl {

    public static void register(ResourceLocation id, LayeredDraw.Layer layer) {
        HudRenderCallback.EVENT.register(layer::render);
    }
}
