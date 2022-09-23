package net.threetag.palladiumcore.permission;

import net.minecraft.network.chat.Component;

public interface IPermissionNode<T> {

    String getPermissionKey();

    PermissionType<T> getPermissionType();

    PermissionResolver<T> getDefaultPermissionResolver();

    IPermissionNode<T> setDisplayInformation(Component name, Component description);
}
