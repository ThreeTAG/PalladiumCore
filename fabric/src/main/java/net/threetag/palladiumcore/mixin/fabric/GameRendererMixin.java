package net.threetag.palladiumcore.mixin.fabric;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.threetag.palladiumcore.event.ViewportEvents;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("DataFlowIssue")
@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow
    @Final
    private Camera mainCamera;

    @Unique
    private float cachedRoll = 0F;

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;resetProjectionMatrix(Lorg/joml/Matrix4f;)V", shift = At.Shift.AFTER))
    private void renderLevelPre(float partialTick, long nanoTime, CallbackInfo ci) {
        AtomicReference<Float> yaw = new AtomicReference<>(this.mainCamera.getYRot());
        AtomicReference<Float> pitch = new AtomicReference<>(this.mainCamera.getXRot());
        AtomicReference<Float> roll = new AtomicReference<>(0F);

        ViewportEvents.COMPUTE_CAMERA_ANGLES.invoker().computeCameraAngles((GameRenderer) (Object) this, this.mainCamera, partialTick, yaw, pitch, roll);

        this.mainCamera.yRot = yaw.get();
        this.mainCamera.xRot = pitch.get();
        this.cachedRoll = roll.get();
    }

    @ModifyVariable(method = "renderLevel", at = @At("STORE"), ordinal = 1)
    private Matrix4f injected(Matrix4f matrix) {
        return new Matrix4f().rotationZ(this.cachedRoll * (float) (Math.PI / 180.0)).mul(matrix);
    }

}
