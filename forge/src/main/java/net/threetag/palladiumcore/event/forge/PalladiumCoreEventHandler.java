package net.threetag.palladiumcore.event.forge;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.*;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.threetag.palladiumcore.PalladiumCore;
import net.threetag.palladiumcore.event.*;

@Mod.EventBusSubscriber(modid = PalladiumCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PalladiumCoreEventHandler {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void registerCommands(RegisterCommandsEvent e) {
        CommandEvents.REGISTER.invoker().register(e.getDispatcher(), e.getCommandSelection());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void playerJoin(PlayerEvent.PlayerLoggedInEvent e) {
        PlayerEvents.JOIN.invoker().playerJoin(e.getEntity());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void playerQuit(PlayerEvent.PlayerLoggedOutEvent e) {
        PlayerEvents.QUIT.invoker().playerQuit(e.getEntity());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void joinLevel(EntityJoinLevelEvent e) {
        EntityEvents.JOIN_LEVEL.invoker().entityJoinLevel(e.getEntity(), e.getLevel());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void livingDeath(LivingDeathEvent e) {
        if (LivingEntityEvents.DEATH.invoker().livingEntityDeath(e.getEntity(), e.getSource()).cancelsEvent()) {
            e.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void livingHurt(LivingHurtEvent e) {
        if (LivingEntityEvents.HURT.invoker().livingEntityHurt(e.getEntity(), e.getSource(), e.getAmount()).cancelsEvent()) {
            e.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void livingTick(LivingEvent.LivingTickEvent e) {
        LivingEntityEvents.TICK.invoker().livingEntityTick(e.getEntity());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void startTracking(PlayerEvent.StartTracking e) {
        PlayerEvents.START_TRACKING.invoker().playerTracking(e.getEntity(), e.getTarget());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void stopTracking(PlayerEvent.StopTracking e) {
        PlayerEvents.STOP_TRACKING.invoker().playerTracking(e.getEntity(), e.getTarget());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void serverAboutToStart(ServerAboutToStartEvent e) {
        LifecycleEvents.SERVER_ABOUT_TO_START.invoker().server(e.getServer());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void serverStarting(ServerStartingEvent e) {
        LifecycleEvents.SERVER_STARTING.invoker().server(e.getServer());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void serverStarted(ServerStartedEvent e) {
        LifecycleEvents.SERVER_STARTED.invoker().server(e.getServer());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void serverStopping(ServerStoppingEvent e) {
        LifecycleEvents.SERVER_STOPPING.invoker().server(e.getServer());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void serverStopped(ServerStoppedEvent e) {
        LifecycleEvents.SERVER_STOPPED.invoker().server(e.getServer());
    }

}
