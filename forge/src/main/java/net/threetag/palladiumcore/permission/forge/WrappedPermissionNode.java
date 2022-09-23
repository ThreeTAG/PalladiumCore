package net.threetag.palladiumcore.permission.forge;

import net.minecraft.network.chat.Component;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import net.threetag.palladiumcore.permission.IPermissionNode;
import net.threetag.palladiumcore.permission.PermissionResolver;
import net.threetag.palladiumcore.permission.PermissionType;

public class WrappedPermissionNode<T> implements IPermissionNode<T> {

    private final PermissionNode<T> forgeNode;
    private final PermissionType<T> type;
    private final PermissionResolver<T> defaultResolver;

    @SuppressWarnings("unchecked")
    public WrappedPermissionNode(PermissionNode<T> forgeNode) {
        this.forgeNode = forgeNode;
        this.type = (PermissionType<T>) PermissionType.getByName(forgeNode.getType().typeName());
        this.defaultResolver = (player, playerUUID) -> forgeNode.getDefaultResolver().resolve(player, playerUUID);
    }

    public PermissionNode<T> getForgeNode() {
        return forgeNode;
    }

    @Override
    public String getPermissionKey() {
        return this.forgeNode.getNodeName();
    }

    @Override
    public PermissionType<T> getPermissionType() {
        return this.type;
    }

    @Override
    public PermissionResolver<T> getDefaultPermissionResolver() {
        return this.defaultResolver;
    }

    @Override
    public IPermissionNode<T> setDisplayInformation(Component name, Component description) {
        this.forgeNode.setInformation(name, description);
        return this;
    }
}
