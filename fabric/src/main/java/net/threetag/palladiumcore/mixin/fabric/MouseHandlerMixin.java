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

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

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

}
