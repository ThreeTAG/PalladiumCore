package net.threetag.palladiumcore.registry.client.forge;

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
@Mod.EventBusSubscriber(modid = PalladiumCore.MOD_ID, value = Dist.CLIENT)
public class OverlayRegistryImpl {

    private static final Map<String, OverlayRegistry.IIngameOverlay> OVERLAYS = new HashMap<>();

    public static void registerOverlay(String displayName, OverlayRegistry.IIngameOverlay overlay) {
        OVERLAYS.put(displayName, overlay);
    }

    @SubscribeEvent
    public static void onRegisterGuiOverlays(RegisterGuiOverlaysEvent e) {
        OVERLAYS.forEach((id, overlay) -> e.registerAboveAll(id, overlay::render));
    }

}
