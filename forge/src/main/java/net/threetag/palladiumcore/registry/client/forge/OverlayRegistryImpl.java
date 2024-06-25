package net.threetag.palladiumcore.registry.client.forge;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.threetag.palladiumcore.PalladiumCore;
import net.threetag.palladiumcore.registry.client.OverlayRegistry;

import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = PalladiumCore.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OverlayRegistryImpl {

    private static final Map<String, OverlayRegistry.IngameOverlay> OVERLAYS = new HashMap<>();

    public static void registerOverlay(String id, OverlayRegistry.IngameOverlay overlay) {
        OVERLAYS.put(id, overlay);
    }

    @SubscribeEvent
    public static void onRegisterGuiOverlays(RegisterGuiOverlaysEvent e) {
        OVERLAYS.forEach((id, overlay) -> e.registerAboveAll(id,
                (forgeGui, poseStack, partialTick, w, h) -> overlay.render(Minecraft.getInstance(), forgeGui, poseStack, partialTick, w, h)));
    }

}
