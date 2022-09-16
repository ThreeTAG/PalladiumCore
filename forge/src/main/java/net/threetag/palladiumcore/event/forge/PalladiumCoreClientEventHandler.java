package net.threetag.palladiumcore.event.forge;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.threetag.palladiumcore.PalladiumCore;
import net.threetag.palladiumcore.event.InputEvents;
import net.threetag.palladiumcore.event.ScreenEvents;

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
    public static void screenInitPre(ScreenEvent.Init.Pre e) {
        if (ScreenEvents.INIT_PRE.invoker().screenInitPre(e.getScreen()).cancelsEvent()) {
            e.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void screenInitPost(ScreenEvent.Init.Post e) {
        ScreenEvents.INIT_POST.invoker().screenInitPost(e.getScreen());
    }

}
