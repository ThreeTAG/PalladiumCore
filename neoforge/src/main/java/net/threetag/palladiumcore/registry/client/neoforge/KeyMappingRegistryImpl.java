package net.threetag.palladiumcore.registry.client.neoforge;

import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.threetag.palladiumcore.PalladiumCore;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = PalladiumCore.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class KeyMappingRegistryImpl {

    private static final List<KeyMapping> KEY_MAPPINGS = new ArrayList<>();

    public static void register(KeyMapping mapping) {
        KEY_MAPPINGS.add(mapping);
    }

    @SubscribeEvent
    public static void event(RegisterKeyMappingsEvent e) {
        for (KeyMapping keyMapping : KEY_MAPPINGS) {
            e.register(keyMapping);
        }
    }

}
