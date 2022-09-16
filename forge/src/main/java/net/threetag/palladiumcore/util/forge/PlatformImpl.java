package net.threetag.palladiumcore.util.forge;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.threetag.palladiumcore.util.Platform;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;

public class PlatformImpl {

    public static boolean isProduction() {
        return FMLLoader.isProduction();
    }

    public static boolean isModLoaded(String id) {
        return ModList.get().isLoaded(id);
    }

    public static Collection<String> getModIds() {
        return ModList.get().getMods().stream().map(IModInfo::getModId).toList();
    }

    public static boolean isClient() {
        return FMLEnvironment.dist == Dist.CLIENT;
    }

    public static boolean isServer() {
        return FMLEnvironment.dist == Dist.DEDICATED_SERVER;
    }

    public static MinecraftServer getCurrentServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

    public static Path getFolder() {
        return FMLPaths.GAMEDIR.get();
    }

    public static Platform.Mod getMod(String modId) {
        var mod = ModList.get().getMods().stream().filter(modInfo -> Objects.equals(modInfo.getModId(), modId)).findFirst().orElse(null);

        if(mod != null) {
            return new Platform.Mod(mod.getModId(), mod.getVersion().toString(), mod.getDisplayName(), mod.getDescription());
        } else {
            return null;
        }
    }
}
