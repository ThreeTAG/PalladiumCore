package net.threetag.palladiumcore.event.forge;

import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.threetag.palladiumcore.PalladiumCore;
import net.threetag.palladiumcore.event.EntityEvents;
import net.threetag.palladiumcore.event.LivingEntityEvents;

@Mod.EventBusSubscriber(modid = PalladiumCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PalladiumCoreEventHandler {

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

}
