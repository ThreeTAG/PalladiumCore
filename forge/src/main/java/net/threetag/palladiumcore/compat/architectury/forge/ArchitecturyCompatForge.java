package net.threetag.palladiumcore.compat.architectury.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.eventbus.api.IEventBus;

public class ArchitecturyCompatForge {

    public static void registerModEventBus(String modId, IEventBus bus) {
        EventBuses.registerModEventBus(modId, bus);
    }

}
