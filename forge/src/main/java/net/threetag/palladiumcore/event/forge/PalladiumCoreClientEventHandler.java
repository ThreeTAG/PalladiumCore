package net.threetag.palladiumcore.event.forge;

import com.mojang.blaze3d.shaders.FogShape;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.threetag.palladiumcore.PalladiumCore;
import net.threetag.palladiumcore.event.*;

import java.util.concurrent.atomic.AtomicReference;

@Mod.EventBusSubscriber(modid = PalladiumCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class PalladiumCoreClientEventHandler {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void inputKey(InputEvent.Key e) {
        InputEvents.KEY_PRESSED.invoker().keyPressed(Minecraft.getInstance(), e.getKey(), e.getScanCode(), e.getAction(), e.getModifiers());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void inputMousePre(InputEvent.MouseButton.Pre e) {
        if (InputEvents.MOUSE_CLICKED_PRE.invoker().mouseClickedPre(Minecraft.getInstance(), e.getButton(), e.getAction(), e.getModifiers()).cancelsEvent()) {
            e.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void inputMousePost(InputEvent.MouseButton.Post e) {
        InputEvents.MOUSE_CLICKED_POST.invoker().mouseClickedPost(Minecraft.getInstance(), e.getButton(), e.getAction(), e.getModifiers());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void screenInitPost(MovementInputUpdateEvent e) {
        InputEvents.MOVEMENT_INPUT_UPDATE.invoker().movementInputUpdate(e.getEntity(), e.getInput());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void screenInitPost(ScreenEvent.Opening e) {
        AtomicReference<Screen> newScreen = new AtomicReference<>(e.getNewScreen());

        if (ScreenEvents.OPENING.invoker().screenOpening(e.getCurrentScreen(), newScreen).cancelsEvent()) {
            e.setCanceled(true);
        }

        if (newScreen.get() != e.getNewScreen()) {
            e.setNewScreen(newScreen.get());
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void screenInitPre(ScreenEvent.Init.Pre e) {
        if (ScreenEvents.INIT_PRE.invoker().screenInitPre(e.getScreen()).cancelsEvent()) {
            e.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void screenInitPost(ScreenEvent.Init.Post e) {
        ScreenEvents.INIT_POST.invoker().screenInitPost(e.getScreen());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void playerJoin(ClientPlayerNetworkEvent.LoggingIn e) {
        PlayerEvents.CLIENT_JOIN.invoker().playerJoin(e.getPlayer());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void playerQuit(ClientPlayerNetworkEvent.LoggingOut e) {
        PlayerEvents.CLIENT_QUIT.invoker().playerQuit(e.getPlayer());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void clientTick(TickEvent.ClientTickEvent e) {
        if (e.phase == TickEvent.Phase.START) {
            ClientTickEvents.CLIENT_PRE.invoker().clientTick(Minecraft.getInstance());
        } else {
            ClientTickEvents.CLIENT_POST.invoker().clientTick(Minecraft.getInstance());
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void computeCameraAngles(ViewportEvent.ComputeCameraAngles e) {
        AtomicReference<Float> yaw = new AtomicReference<>(e.getYaw());
        AtomicReference<Float> pitch = new AtomicReference<>(e.getPitch());
        AtomicReference<Float> roll = new AtomicReference<>(e.getRoll());

        ViewportEvents.COMPUTE_CAMERA_ANGLES.invoker().computeCameraAngles(e.getRenderer(), e.getCamera(), e.getPartialTick(), yaw, pitch, roll);

        e.setYaw(yaw.get());
        e.setPitch(pitch.get());
        e.setRoll(roll.get());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void renderFog(ViewportEvent.RenderFog e) {
        AtomicReference<Float> fpd = new AtomicReference<>(e.getFarPlaneDistance());
        AtomicReference<Float> npd = new AtomicReference<>(e.getNearPlaneDistance());
        AtomicReference<FogShape> shape = new AtomicReference<>(e.getFogShape());

        var event = ViewportEvents.RENDER_FOG.invoker().renderFog(e.getRenderer(), e.getCamera(), e.getPartialTick(), e.getMode(), e.getType(), fpd, npd, shape);

        e.setNearPlaneDistance(npd.get());
        e.setFarPlaneDistance(fpd.get());
        e.setFogShape(shape.get());

        if (event.cancelsEvent()) {
            e.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void computeFogColor(ViewportEvent.ComputeFogColor e) {
        AtomicReference<Float> red = new AtomicReference<>(e.getRed());
        AtomicReference<Float> green = new AtomicReference<>(e.getGreen());
        AtomicReference<Float> blue = new AtomicReference<>(e.getBlue());

        ViewportEvents.COMPUTE_FOG_COLOR.invoker().computeFogColor(e.getRenderer(), e.getCamera(), e.getPartialTick(), red, green, blue);

        e.setRed(red.get());
        e.setGreen(green.get());
        e.setBlue(blue.get());
    }

}
