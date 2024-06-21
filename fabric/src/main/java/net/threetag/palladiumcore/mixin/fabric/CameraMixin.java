package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.threetag.palladiumcore.event.ViewportEvents;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.concurrent.atomic.AtomicReference;

@Mixin(Camera.class)
public class CameraMixin {

    @Unique
    private static final Vector3f FORWARDS = new Vector3f(0.0F, 0.0F, -1.0F);
    @Unique
    private static final Vector3f UP = new Vector3f(0.0F, 1.0F, 0.0F);
    @Unique
    private static final Vector3f LEFT = new Vector3f(-1.0F, 0.0F, 0.0F);

    @Shadow
    public float xRot;

    @Shadow
    public float yRot;

    @Shadow
    @Final
    private Quaternionf rotation;

    @Shadow
    private float partialTickTime;

    @Shadow
    private Entity entity;

    @Shadow
    @Final
    private Vector3f forwards;

    @Shadow
    @Final
    private Vector3f up;

    @Shadow
    @Final
    private Vector3f left;

    @Unique
    private float roll;

    @Redirect(method = "setup", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;setRotation(FF)V", ordinal = 0))
    private void injected(Camera instance, float yRot, float xRot) {
        AtomicReference<Float> yaw = new AtomicReference<>(this.entity.getViewYRot(this.partialTickTime));
        AtomicReference<Float> pitch = new AtomicReference<>(this.entity.getViewXRot(this.partialTickTime));
        AtomicReference<Float> roll = new AtomicReference<>(0F);

        ViewportEvents.COMPUTE_CAMERA_ANGLES.invoker().computeCameraAngles((Camera) (Object) this, this.partialTickTime, yaw, pitch, roll);

        this.setRotation_(yaw.get(), pitch.get(), roll.get());
    }

    @Unique
    protected void setRotation_(float f, float g, float roll) {
        this.xRot = g;
        this.yRot = f;
        this.roll = roll;
        this.rotation.rotationYXZ((float) Math.PI - f * (float) (Math.PI / 180.0), -g * (float) (Math.PI / 180.0), -roll * (float) (Math.PI / 180.0));
        FORWARDS.rotate(this.rotation, this.forwards);
        UP.rotate(this.rotation, this.up);
        LEFT.rotate(this.rotation, this.left);
    }

}
