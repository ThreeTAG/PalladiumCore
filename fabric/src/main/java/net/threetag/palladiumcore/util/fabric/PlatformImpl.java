package net.threetag.palladiumcore.util.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.threetag.palladiumcore.event.LifecycleEvents;
import net.threetag.palladiumcore.util.Platform;

import java.nio.file.Path;
import java.util.Collection;

public class PlatformImpl {

    public static MinecraftServer CACHED_SERVER = null;

    public static boolean isProduction() {
        return !FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    public static boolean isModLoaded(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
    }

    public static Collection<String> getModIds() {
        return FabricLoader.getInstance().getAllMods().stream().map(ModContainer::getMetadata).map(ModMetadata::getId).toList();
    }

    public static boolean isClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    public static boolean isServer() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER;
    }

    public static boolean isForge() {
        return false;
    }

    public static boolean isFabric() {
        return true;
    }

    public static MinecraftServer getCurrentServer() {
        if (CACHED_SERVER != null) {
            return CACHED_SERVER;
        }

        if (isClient()) {
            return getFromClient();
        }

        return null;
    }

    @Environment(EnvType.CLIENT)
    private static MinecraftServer getFromClient() {
        return Minecraft.getInstance().getSingleplayerServer();
    }

    public static void init() {
        LifecycleEvents.SERVER_ABOUT_TO_START.register(server -> CACHED_SERVER = server);
        LifecycleEvents.SERVER_STOPPED.register(server -> CACHED_SERVER = null);
    }

    public static Path getFolder() {
        return FabricLoader.getInstance()
                .getGameDir()
                .toAbsolutePath()
                .normalize();
    }

    public static Platform.Mod getMod(String modId) {
        var mod = FabricLoader.getInstance().getModContainer(modId).orElse(null);

        if (mod != null) {
            return new Platform.Mod(mod.getMetadata().getId(), mod.getMetadata().getVersion().getFriendlyString(), mod.getMetadata().getName(), mod.getMetadata().getDescription());
        } else {
            return null;
        }
    }

}
