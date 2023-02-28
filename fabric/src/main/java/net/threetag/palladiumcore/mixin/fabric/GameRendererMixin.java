package net.threetag.palladiumcore.mixin.fabric;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.threetag.palladiumcore.event.ViewportEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicReference;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow
    @Final
    private Camera mainCamera;

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;setup(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/entity/Entity;ZZF)V", shift = At.Shift.AFTER))
    private void renderLevel(float partialTicks, long finishTimeNano, PoseStack matrixStack, CallbackInfo ci) {
        AtomicReference<Float> yaw = new AtomicReference<>(this.mainCamera.getYRot());
        AtomicReference<Float> pitch = new AtomicReference<>(this.mainCamera.getXRot());
        AtomicReference<Float> roll = new AtomicReference<>(0F);

        ViewportEvents.COMPUTE_CAMERA_ANGLES.invoker().computeCameraAngles((GameRenderer) (Object) this, this.mainCamera, partialTicks, yaw, pitch, roll);

        this.mainCamera.yRot = yaw.get();
        this.mainCamera.xRot = pitch.get();
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(roll.get()));
    }

}
