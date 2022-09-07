package net.threetag.palladiumcore.registry.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.threetag.palladiumcore.PalladiumCore;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = PalladiumCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
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

    @Mod.EventBusSubscriber(modid = PalladiumCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Client {

        @SubscribeEvent
        public static void addReloadListeners(RegisterClientReloadListenersEvent e) {
            for (PreparableReloadListener listener : CLIENT_LISTENERS) {
                e.registerReloadListener(listener);
            }
        }

    }

}
