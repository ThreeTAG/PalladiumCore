package net.threetag.palladiumcore.registry.client.forge;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.threetag.palladiumcore.PalladiumCore;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = PalladiumCore.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
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
