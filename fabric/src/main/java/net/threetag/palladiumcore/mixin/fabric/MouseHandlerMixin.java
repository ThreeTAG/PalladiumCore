package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.threetag.palladiumcore.event.InputEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    public abstract boolean isLeftPressed();

    @Shadow
    public abstract boolean isMiddlePressed();

    @Shadow
    public abstract boolean isRightPressed();

    @Shadow
    public abstract double xpos();

    @Shadow
    public abstract double ypos();

    @Inject(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/Minecraft;getOverlay()Lnet/minecraft/client/gui/screens/Overlay;",
            ordinal = 0),
            method = "onPress", cancellable = true)
    private void onPressPre(long pWindowPointer, int pButton, int pAction, int pModifiers, CallbackInfo ci) {
        if (InputEvents.MOUSE_CLICKED_PRE.invoker().mouseClickedPre(this.minecraft, pButton, pAction, pModifiers).cancelsEvent()) {
            ci.cancel();
        }
    }

    @Inject(at = @At("TAIL"), method = "onPress")
    private void onPressPost(long pWindowPointer, int pButton, int pAction, int pModifiers, CallbackInfo ci) {
        if (pWindowPointer == this.minecraft.getWindow().getWindow()) {
            InputEvents.MOUSE_CLICKED_POST.invoker().mouseClickedPost(this.minecraft, pButton, pAction, pModifiers);
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isSpectator()Z"),
            method = "onScroll", cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void onScroll(long windowPointer, double xOffset, double yOffset, CallbackInfo ci, double amount) {
        if (InputEvents.MOUSE_SCROLLING.invoker().mouseScrolling(Minecraft.getInstance(), amount, this.isLeftPressed(), this.isMiddlePressed(), this.isRightPressed(), this.xpos(), this.ypos()).cancelsEvent()) {
            ci.cancel();
        }
    }

}
