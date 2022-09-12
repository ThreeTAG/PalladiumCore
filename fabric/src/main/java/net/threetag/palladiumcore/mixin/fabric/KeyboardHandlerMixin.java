package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.threetag.palladiumcore.event.InputEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(at = @At("TAIL"), method = "keyPress")
    private void keyPress(long pWindowPointer, int pKey, int pScanCode, int pAction, int pModifiers, CallbackInfo ci) {
        if (pWindowPointer == this.minecraft.getWindow().getWindow()) {
            InputEvents.KEY_PRESSED.invoker().keyPressed(Minecraft.getInstance(), pKey, pScanCode, pAction, pModifiers);
        }
    }

}
