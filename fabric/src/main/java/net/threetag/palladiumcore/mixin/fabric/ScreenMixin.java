package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.threetag.palladiumcore.event.ScreenEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("ConstantConditions")
@Mixin(Screen.class)
public class ScreenMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;rebuildWidgets()V", ordinal = 0),
            method = "init(Lnet/minecraft/client/Minecraft;II)V", cancellable = true)
    private void initPre(Minecraft pMinecraft, int pWidth, int pHeight, CallbackInfo ci) {
        if (ScreenEvents.INIT_PRE.invoker().screenInitPre((Screen) (Object) this).cancelsEvent()) {
            ci.cancel();
        }
    }

    @Inject(at = @At("TAIL"), method = "init(Lnet/minecraft/client/Minecraft;II)V")
    private void initPost(Minecraft pMinecraft, int pWidth, int pHeight, CallbackInfo ci) {
        ScreenEvents.INIT_POST.invoker().screenInitPost((Screen) (Object) this);
    }

}
