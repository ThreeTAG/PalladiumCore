package net.threetag.palladiumcore.registry.neoforge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.threetag.palladiumcore.PalladiumCore;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = PalladiumCore.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ReloadListenerRegistryImpl {

    private static final List<PreparableReloadListener> SERVER_LISTENERS = new ArrayList<>();
    private static final List<PreparableReloadListener> CLIENT_LISTENERS = new ArrayList<>();

    public static void register(PackType type, ResourceLocation id, PreparableReloadListener listener) {
        if (type == PackType.SERVER_DATA) {
            SERVER_LISTENERS.add(listener);
        } else if (type == PackType.CLIENT_RESOURCES) {
            CLIENT_LISTENERS.add(listener);
        }
    }

    @SubscribeEvent
    public static void addReloadListeners(AddReloadListenerEvent e) {
        for (PreparableReloadListener listener : SERVER_LISTENERS) {
            e.addListener(listener);
        }
    }

    @EventBusSubscriber(modid = PalladiumCore.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Client {

        @SubscribeEvent
        public static void addReloadListeners(RegisterClientReloadListenersEvent e) {
            for (PreparableReloadListener listener : CLIENT_LISTENERS) {
                e.registerReloadListener(listener);
            }
        }

    }

}
