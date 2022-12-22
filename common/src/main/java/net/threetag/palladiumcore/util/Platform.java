package net.threetag.palladiumcore.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.Collection;

public class Platform {

    private static byte ARCHITECTURY_LOADED = 0;

    @ExpectPlatform
    public static boolean isProduction() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isModLoaded(String id) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Collection<String> getModIds() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isClient() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isServer() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isForge() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isFabric() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static MinecraftServer getCurrentServer() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Path getFolder() {
        throw new AssertionError();
    }

    @ExpectPlatform
    @Nullable
    public static Mod getMod(String modId) {
        throw new AssertionError();
    }

    public static boolean isArchitecturyLoaded() {
        if (ARCHITECTURY_LOADED == 0) {
            ARCHITECTURY_LOADED = (byte) (isModLoaded("architectury") ? 2 : 1);
        }
        return ARCHITECTURY_LOADED == 2;
    }

    public record Mod(String modId, String version, String name, String description) {

    }

}
