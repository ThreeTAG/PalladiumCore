package net.threetag.palladiumcore.event;

import com.mojang.blaze3d.shaders.FogShape;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.level.material.FogType;

import java.util.concurrent.atomic.AtomicReference;

public interface ViewportEvents {

    /**
     * @see ComputeCameraAngles#computeCameraAngles(GameRenderer, Camera, double, AtomicReference, AtomicReference, AtomicReference) 
     */
    Event<ComputeCameraAngles> COMPUTE_CAMERA_ANGLES = new Event<>(ComputeCameraAngles.class, listeners -> (gr, c, pt, y, p, r) -> {
        for (ComputeCameraAngles listener : listeners) {
            listener.computeCameraAngles(gr, c, pt, y, p, r);
        }
    });
    
    /**
     * @see RenderFog#renderFog(GameRenderer, Camera, double, FogRenderer.FogMode, FogType, AtomicReference, AtomicReference, AtomicReference)
     */
    Event<RenderFog> RENDER_FOG = new Event<>(RenderFog.class, listeners -> (gr, c, pt, fm, ft, fpd, npd, fs) ->
            Event.result(listeners, renderFog -> renderFog.renderFog(gr, c, pt, fm, ft, fpd, npd, fs)));

    /**
     * @see ComputeFogColor#computeFogColor(GameRenderer, Camera, double, AtomicReference, AtomicReference, AtomicReference)
     */
    Event<ComputeFogColor> COMPUTE_FOG_COLOR = new Event<>(ComputeFogColor.class, listeners -> (gr, c, pt, r, g, b) -> {
        for (ComputeFogColor listener : listeners) {
            listener.computeFogColor(gr, c, pt, r, g, b);
        }
    });

    @FunctionalInterface
    interface ComputeCameraAngles {

        void computeCameraAngles(GameRenderer gameRenderer, Camera camera, double partialTick,
                                 AtomicReference<Float> yaw, AtomicReference<Float> pitch, AtomicReference<Float> roll);

    }

    @FunctionalInterface
    interface RenderFog {

        /**
         * Used for rendering custom fog. Must be cancelled to effect the plane distances.
         *
         * @param fogMode           Mode of the fog being rendered
         * @param fogType           Type of the fog being rendered
         * @param farPlaneDistance  Distance to the far plane where the fog ends
         * @param nearPlaneDistance Distance to the near plane where the fog starts
         * @param fogShape          Shape of the fog being rendered
         * @return EventResult that determines to effect the plane distances
         */
        EventResult renderFog(GameRenderer gameRenderer, Camera camera, double partialTick, FogRenderer.FogMode fogMode,
                              FogType fogType, AtomicReference<Float> farPlaneDistance,
                              AtomicReference<Float> nearPlaneDistance, AtomicReference<FogShape> fogShape);

    }

    @FunctionalInterface
    interface ComputeFogColor {

        /**
         * Fired for customizing the color of the fog visible to the player.
         *
         * @param gameRenderer Game renderer
         * @param camera       Camera information
         * @param partialTick  Partial Tick
         * @param red          Red color of the fog
         * @param green        Green color of the fog
         * @param blue         Blue color of the fog
         */
        void computeFogColor(GameRenderer gameRenderer, Camera camera, double partialTick, AtomicReference<Float> red, AtomicReference<Float> green, AtomicReference<Float> blue);

    }

}
