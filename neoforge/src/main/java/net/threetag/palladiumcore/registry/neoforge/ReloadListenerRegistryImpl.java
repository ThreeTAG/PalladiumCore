package net.threetag.palladiumcore.registry.neoforge;

import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.threetag.palladiumcore.PalladiumCore;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@EventBusSubscriber(modid = PalladiumCore.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ReloadListenerRegistryImpl {

    private static final List<Function<HolderLookup.Provider, PreparableReloadListener>> SERVER_LISTENERS = new ArrayList<>();
    private static final List<PreparableReloadListener> CLIENT_LISTENERS = new ArrayList<>();

    public static void registerClientListener(ResourceLocation id, PreparableReloadListener listener) {
        CLIENT_LISTENERS.add(listener);
    }

    public static void registerServerListener(ResourceLocation id, Function<HolderLookup.Provider, PreparableReloadListener> listener) {
        SERVER_LISTENERS.add(listener);
    }

    @SubscribeEvent
    public static void addReloadListeners(AddReloadListenerEvent e) {
        for (Function<HolderLookup.Provider, PreparableReloadListener> listener : SERVER_LISTENERS) {
            e.addListener(listener.apply(e.getRegistryAccess()));
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
