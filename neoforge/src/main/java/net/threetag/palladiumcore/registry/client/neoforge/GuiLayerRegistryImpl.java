package net.threetag.palladiumcore.registry.client.neoforge;

import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.threetag.palladiumcore.PalladiumCore;

import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = PalladiumCore.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class GuiLayerRegistryImpl {

    private static final Map<ResourceLocation, LayeredDraw.Layer> LAYERS = new HashMap<>();

    public static void register(ResourceLocation id, LayeredDraw.Layer layer) {
        LAYERS.put(id, layer);
    }

    @SubscribeEvent
    public static void onRegisterGuiOverlays(RegisterGuiLayersEvent e) {
        LAYERS.forEach(e::registerAboveAll);
    }

}
