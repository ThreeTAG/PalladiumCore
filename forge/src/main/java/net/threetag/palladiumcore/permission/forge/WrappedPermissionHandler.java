package net.threetag.palladiumcore.permission.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.permission.handler.IPermissionHandler;
import net.minecraftforge.server.permission.nodes.PermissionDynamicContext;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import net.threetag.palladiumcore.permission.IPermissionNode;
import net.threetag.palladiumcore.permission.PermissionHandler;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings("unchecked")
public class WrappedPermissionHandler implements IPermissionHandler {

    private final PermissionHandler permissionHandler;
    private final Set<PermissionNode<?>> permissions;

    public WrappedPermissionHandler(PermissionHandler permissionHandler, Collection<PermissionNode<?>> permissions) {
        this.permissionHandler = permissionHandler;
        this.permissions = Set.copyOf(permissions);
    }

    @Override
    public ResourceLocation getIdentifier() {
        return this.permissionHandler.getIdentifier();
    }

    @Override
    public Set<PermissionNode<?>> getRegisteredNodes() {
        return this.permissions;
    }

    @Override
    public <T> T getPermission(ServerPlayer arg, PermissionNode<T> permissionNode, PermissionDynamicContext<?>... permissionDynamicContexts) {
        IPermissionNode<T> node = (IPermissionNode<T>) PermissionManagerImpl.PERMISSIONS.get(permissionNode);
        return this.permissionHandler.getPermission(arg, node);
    }

    @Override
    public <T> T getOfflinePermission(UUID uUID, PermissionNode<T> permissionNode, PermissionDynamicContext<?>... permissionDynamicContexts) {
        IPermissionNode<T> node = (IPermissionNode<T>) PermissionManagerImpl.PERMISSIONS.get(permissionNode);
        return this.permissionHandler.getOfflinePermission(uUID, node);
    }
}
