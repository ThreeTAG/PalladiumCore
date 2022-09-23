package net.threetag.palladiumcore.permission;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public class PermissionManager {

    @ExpectPlatform
    public static void registerPermissionManager(PermissionHandler permissionHandler) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T> IPermissionNode<T> registerPermission(ResourceLocation key, PermissionType<T> type, PermissionResolver<T> defaultResolver) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T> T getPermission(ServerPlayer player, IPermissionNode<T> node) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T> T getOfflinePermission(UUID player, IPermissionNode<T> node) {
        throw new AssertionError();
    }

}
