package net.threetag.palladiumcore.event.forge;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.server.*;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.threetag.palladiumcore.PalladiumCore;
import net.threetag.palladiumcore.event.*;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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
    public static void playerClone(PlayerEvent.Clone e) {
        PlayerEvents.CLONE.invoker().playerClone(e.getOriginal(), e.getEntity(), e.isWasDeath());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void playerRespawn(PlayerEvent.PlayerRespawnEvent e) {
        PlayerEvents.RESPAWN.invoker().playerRespawn(e.getEntity(), e.isEndConquered());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void playerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent e) {
        PlayerEvents.CHANGED_DIMENSION.invoker().playerChangedDimension(e.getEntity(), e.getTo());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void playerChangedDimension(PlayerEvent.NameFormat e) {
        AtomicReference<Component> name = new AtomicReference<>(e.getDisplayname());
        PlayerEvents.NAME_FORMAT.invoker().playerNameFormat(e.getEntity(), e.getUsername(), name);
        e.setDisplayname(name.get());
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
        AtomicReference<Float> amount = new AtomicReference<>(e.getAmount());
        if (LivingEntityEvents.HURT.invoker().livingEntityHurt(e.getEntity(), e.getSource(), amount).cancelsEvent()) {
            e.setCanceled(true);
        }
        e.setAmount(amount.get());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void livingAttack(LivingAttackEvent e) {
        if (LivingEntityEvents.ATTACK.invoker().livingEntityAttack(e.getEntity(), e.getSource(), e.getAmount()).cancelsEvent()) {
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

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onDatapackSync(OnDatapackSyncEvent e) {
        LifecycleEvents.DATAPACK_SYNC.invoker().onDatapackSync(e.getPlayerList(), e.getPlayer());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void anvilUpdate(AnvilUpdateEvent e) {
        AtomicInteger cost = new AtomicInteger(e.getCost());
        AtomicInteger materialCost = new AtomicInteger(e.getMaterialCost());
        AtomicReference<ItemStack> output = new AtomicReference<>(e.getOutput());

        if (PlayerEvents.ANVIL_UPDATE.invoker().anvilUpdate(e.getPlayer(), e.getLeft(), e.getRight(), e.getName(), cost, materialCost, output).cancelsEvent()) {
            e.setCanceled(true);
        }

        e.setCost(cost.get());
        e.setMaterialCost(materialCost.get());
        e.setOutput(output.get());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void livingEntityUseItemStart(LivingEntityUseItemEvent.Start e) {
        AtomicInteger duration = new AtomicInteger(e.getDuration());
        if (LivingEntityEvents.ITEM_USE_START.invoker().livingEntityItemUse(e.getEntity(), e.getItem(), duration).cancelsEvent()) {
            e.setCanceled(true);
        }
        e.setDuration(duration.get());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void livingEntityUseItemTick(LivingEntityUseItemEvent.Tick e) {
        AtomicInteger duration = new AtomicInteger(e.getDuration());
        if (LivingEntityEvents.ITEM_USE_TICK.invoker().livingEntityItemUse(e.getEntity(), e.getItem(), duration).cancelsEvent()) {
            e.setCanceled(true);
        }
        e.setDuration(duration.get());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void livingEntityUseItemStop(LivingEntityUseItemEvent.Stop e) {
        AtomicInteger duration = new AtomicInteger(e.getDuration());
        if (LivingEntityEvents.ITEM_USE_STOP.invoker().livingEntityItemUse(e.getEntity(), e.getItem(), duration).cancelsEvent()) {
            e.setCanceled(true);
        }
        e.setDuration(duration.get());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void livingEntityUseItemFinish(LivingEntityUseItemEvent.Finish e) {
        AtomicInteger duration = new AtomicInteger(e.getDuration());
        LivingEntityEvents.ITEM_USE_FINISH.invoker().livingEntityItemUseFinish(e.getEntity(), e.getItem(), duration);
        e.setDuration(duration.get());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onBlockBreak(BlockEvent.BreakEvent e) {
        if (BlockEvents.BREAK.invoker().breakBlock(e.getLevel(), e.getPos(), e.getState(), e.getPlayer()).cancelsEvent()) {
            e.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent e) {
        if (BlockEvents.PLACE.invoker().placeBlock(e.getLevel(), e.getPos(), e.getPlacedBlock(), e.getPlacedAgainst(), e.getEntity()).cancelsEvent()) {
            e.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onSubmitted(ServerChatEvent.Submitted e) {
        if (ChatEvents.SERVER_SUBMITTED.invoker().chatMessageSubmitted(e.getPlayer(), e.getRawText(), e.getMessage()).cancelsEvent()) {
            e.setCanceled(true);
        }
    }

}
