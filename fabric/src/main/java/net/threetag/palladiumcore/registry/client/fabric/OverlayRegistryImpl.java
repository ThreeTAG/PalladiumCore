package net.threetag.palladiumcore.registry.client.fabric;

import com.mojang.blaze3d.platform.Window;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.threetag.palladiumcore.PalladiumCore;
import net.threetag.palladiumcore.registry.client.OverlayRegistry;

@Environment(EnvType.CLIENT)
public class OverlayRegistryImpl {

    public static void registerOverlay(String id, OverlayRegistry.IIngameOverlay overlay) {
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
            try {
                Minecraft mc = Minecraft.getInstance();
                Gui gui = mc.gui;
                Window window = mc.getWindow();
                overlay.render(mc, gui, matrixStack, tickDelta, window.getGuiScaledWidth(), window.getGuiScaledHeight());
            } catch (Exception e) {
                PalladiumCore.LOGGER.error("Error rendering overlay '{}'", id, e);
            }
        });
    }
}
