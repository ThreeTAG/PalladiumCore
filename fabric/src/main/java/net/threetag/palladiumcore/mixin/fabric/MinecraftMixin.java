package net.threetag.palladiumcore.mixin.fabric;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.BufferUploader;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import net.threetag.palladiumcore.event.PlayerEvents;
import net.threetag.palladiumcore.event.ScreenEvents;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("ConstantConditions")
@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    @Shadow
    @Nullable
    public LocalPlayer player;

    @Shadow
    @Nullable
    public Screen screen;

    @Shadow
    @Final
    public MouseHandler mouseHandler;

    @Shadow
    @Final
    private Window window;

    @Shadow
    public boolean noRender;

    @Shadow
    @Final
    private SoundManager soundManager;

    @Shadow
    public abstract void updateTitle();

    @Inject(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/Minecraft;updateScreenAndTick(Lnet/minecraft/client/gui/screens/Screen;)V",
            ordinal = 0),
            method = "clearLevel(Lnet/minecraft/client/gui/screens/Screen;)V")
    private void clearLevel(Screen pScreen, CallbackInfo ci) {
        PlayerEvents.CLIENT_QUIT.invoker().playerQuit(this.player);
    }

    @Inject(at = @At(value = "INVOKE",
            target = "Lcom/mojang/blaze3d/vertex/BufferUploader;reset()V",
            ordinal = 0,
            shift = At.Shift.BY, by = -1),
            method = "setScreen")
    private void setScreen(@javax.annotation.Nullable Screen pGuiScreen, CallbackInfo ci) {
        AtomicReference<Screen> newScreen = new AtomicReference<>(pGuiScreen);

        if (ScreenEvents.OPENING.invoker().screenOpening(this.screen, newScreen).cancelsEvent()) {
            ci.cancel();
            return;
        }

        if (newScreen.get() != pGuiScreen) {
            pGuiScreen = newScreen.get();
            this.screen = pGuiScreen;
            BufferUploader.reset();
            if (pGuiScreen != null) {
                this.mouseHandler.releaseMouse();
                KeyMapping.releaseAll();
                pGuiScreen.init((Minecraft) (Object) this, this.window.getGuiScaledWidth(), this.window.getGuiScaledHeight());
                this.noRender = false;
            } else {
                this.soundManager.resume();
                this.mouseHandler.grabMouse();
            }

            this.updateTitle();
            ci.cancel();
        }
    }

}
