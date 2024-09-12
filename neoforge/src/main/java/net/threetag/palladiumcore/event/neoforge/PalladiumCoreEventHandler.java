package net.threetag.palladiumcore.event.neoforge;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.server.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.threetag.palladiumcore.PalladiumCore;
import net.threetag.palladiumcore.event.*;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@EventBusSubscriber(modid = PalladiumCore.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class PalladiumCoreEventHandler {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void registerCommands(RegisterCommandsEvent e) {
        CommandEvents.REGISTER.invoker().register(e.getDispatcher(), e.getBuildContext(), e.getCommandSelection());
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
    public static void livingIncomingDamage(LivingIncomingDamageEvent e) {
        var ref = new AtomicReference<>(e.getAmount());
        if (LivingEntityEvents.INCOMING_DAMAGE.invoker().livingIncomingDamage(e.getEntity(), e.getSource(), ref).cancelsEvent()) {
            e.setCanceled(true);
        }
        e.setAmount(ref.get());
    }

        @SubscribeEvent(priority = EventPriority.HIGH)
    public static void livingHurt(LivingDamageEvent.Post e) {
        LivingEntityEvents.DAMAGE_POST.invoker().livingDamagePost(e.getEntity(), e.getSource(), e.getNewDamage());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void livingTickPre(EntityTickEvent.Pre e) {
        EntityEvents.TICK_PRE.invoker().entityTick(e.getEntity());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void livingTickPost(EntityTickEvent.Post e) {
        EntityEvents.TICK_POST.invoker().entityTick(e.getEntity());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void livingJump(LivingEvent.LivingJumpEvent e) {
        LivingEntityEvents.JUMP.invoker().livingEntityJump(e.getEntity());
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
        AtomicLong cost = new AtomicLong(e.getCost());
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
    public static void onSubmitted(ServerChatEvent e) {
        if (ChatEvents.SERVER_SUBMITTED.invoker().chatMessageSubmitted(e.getPlayer(), e.getRawText(), e.getMessage()).cancelsEvent()) {
            e.setCanceled(true);
        }
    }

}
