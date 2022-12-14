package net.threetag.palladiumcore.event.forge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.threetag.palladiumcore.PalladiumCore;
import net.threetag.palladiumcore.event.ClientTickEvents;
import net.threetag.palladiumcore.event.InputEvents;
import net.threetag.palladiumcore.event.PlayerEvents;
import net.threetag.palladiumcore.event.ScreenEvents;

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

}
