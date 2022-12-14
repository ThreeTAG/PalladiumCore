package net.threetag.palladiumcore.registry.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

@Environment(EnvType.CLIENT)
public class OverlayRegistry {

    @ExpectPlatform
    public static void registerOverlay(String id, IIngameOverlay overlay) {
        throw new AssertionError();
    }

    public interface IIngameOverlay {

        void render(Minecraft minecraft, Gui gui, PoseStack mStack, float partialTicks, int width, int height);

    }

}
