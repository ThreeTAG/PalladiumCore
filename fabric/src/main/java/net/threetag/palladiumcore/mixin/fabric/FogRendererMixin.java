package net.threetag.palladiumcore.mixin.fabric;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.FogType;
import net.threetag.palladiumcore.event.ViewportEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.concurrent.atomic.AtomicReference;

@Mixin(FogRenderer.class)
public class FogRendererMixin {

    @Shadow
    private static float fogRed;

    @Shadow
    private static float fogGreen;

    @Shadow
    private static float fogBlue;

    @Inject(method = "setupFog", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void setupFog(Camera camera, FogRenderer.FogMode fogMode, float farPlaneDistance, boolean bl, float partialTick, CallbackInfo ci, FogType fogType, Entity entity, FogRenderer.FogData fogData) {
        AtomicReference<Float> fpd = new AtomicReference<>(fogData.end);
        AtomicReference<Float> npd = new AtomicReference<>(fogData.start);
        AtomicReference<FogShape> shape = new AtomicReference<>(fogData.shape);

        var event = ViewportEvents.RENDER_FOG.invoker().renderFog(Minecraft.getInstance().gameRenderer, camera, partialTick, fogMode, fogType, fpd, npd, shape);

        if (event.cancelsEvent()) {
            RenderSystem.setShaderFogStart(npd.get());
            RenderSystem.setShaderFogEnd(fpd.get());
            RenderSystem.setShaderFogShape(shape.get());
        }
    }

    @Inject(method = "setupColor", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;clearColor(FFFF)V", ordinal = 1))
    private static void setupColor(Camera activeRenderInfo, float partialTicks, ClientLevel level, int renderDistanceChunks, float bossColorModifier, CallbackInfo ci) {
        AtomicReference<Float> red = new AtomicReference<>(fogRed);
        AtomicReference<Float> green = new AtomicReference<>(fogGreen);
        AtomicReference<Float> blue = new AtomicReference<>(fogBlue);

        ViewportEvents.COMPUTE_FOG_COLOR.invoker().computeFogColor(Minecraft.getInstance().gameRenderer, activeRenderInfo, partialTicks, red, green, blue);

        fogRed = red.get();
        fogGreen = green.get();
        fogBlue = blue.get();
    }

}
