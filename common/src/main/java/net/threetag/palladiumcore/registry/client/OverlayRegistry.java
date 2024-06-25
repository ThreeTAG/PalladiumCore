package net.threetag.palladiumcore.registry.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;

@Environment(EnvType.CLIENT)
public class OverlayRegistry {

    @ExpectPlatform
    public static void registerOverlay(String id, IngameOverlay overlay) {
        throw new AssertionError();
    }

    public interface IngameOverlay {

        void render(Minecraft minecraft, Gui gui, GuiGraphics guiGraphics, float partialTicks, int width, int height);

    }

}
