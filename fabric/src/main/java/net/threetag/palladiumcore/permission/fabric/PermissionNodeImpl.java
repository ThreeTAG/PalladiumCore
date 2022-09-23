package net.threetag.palladiumcore.permission.fabric;

import net.minecraft.network.chat.Component;
import net.threetag.palladiumcore.permission.IPermissionNode;
import net.threetag.palladiumcore.permission.PermissionResolver;
import net.threetag.palladiumcore.permission.PermissionType;

public class PermissionNodeImpl<T> implements IPermissionNode<T> {

    private final String key;
    private final PermissionType<T> type;
    private final PermissionResolver<T> defaultResolver;
    private Component name, description;

    public PermissionNodeImpl(String key, PermissionType<T> type, PermissionResolver<T> defaultResolver) {
        this.key = key;
        this.type = type;
        this.defaultResolver = defaultResolver;
    }

    @Override
    public String getPermissionKey() {
        return this.key;
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
        this.name = name;
        this.description = description;
        return this;
    }
}
