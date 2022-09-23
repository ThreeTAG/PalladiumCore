package net.threetag.palladiumcore.permission;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Set;
import java.util.UUID;

public interface PermissionHandler {

    ResourceLocation getIdentifier();

    Set<IPermissionNode<?>> getRegisteredNodes();

    <T> T getPermission(ServerPlayer player, IPermissionNode<T> node);

    <T> T getOfflinePermission(UUID player, IPermissionNode<T> node);

}
