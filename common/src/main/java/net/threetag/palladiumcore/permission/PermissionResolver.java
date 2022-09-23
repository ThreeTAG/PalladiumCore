package net.threetag.palladiumcore.permission;

import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface PermissionResolver<T> {

    PermissionResolver<Boolean> IS_ADMIN = (player, playerUUID) -> player != null && player.hasPermissions(3);
    PermissionResolver<Boolean> IS_USER = (player, playerUUID) -> true;

    T resolve(@Nullable ServerPlayer player, UUID playerUUID);

}
