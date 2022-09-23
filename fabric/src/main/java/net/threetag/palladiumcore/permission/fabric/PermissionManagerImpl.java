package net.threetag.palladiumcore.permission.fabric;

import me.lucko.fabric.api.permissions.v0.OptionRequestEvent;
import me.lucko.fabric.api.permissions.v0.Options;
import me.lucko.fabric.api.permissions.v0.PermissionCheckEvent;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.threetag.palladiumcore.PalladiumCore;
import net.threetag.palladiumcore.permission.IPermissionNode;
import net.threetag.palladiumcore.permission.PermissionHandler;
import net.threetag.palladiumcore.permission.PermissionResolver;
import net.threetag.palladiumcore.permission.PermissionType;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@SuppressWarnings({"unchecked", "rawtypes"})
public class PermissionManagerImpl {

    private static PermissionHandler HANDLER;
    private static final Map<String, IPermissionNode<?>> PERMISSIONS = new HashMap<>();

    public static void init() {
        PermissionCheckEvent.EVENT.register((source, perm) -> {
            if (HANDLER == null) {
                return TriState.DEFAULT;
            }

            var player = getPlayerFromSource(source);

            if (player == null) {
                return TriState.DEFAULT;
            }

            var node = PERMISSIONS.get(perm);

            if (node.getPermissionType() == PermissionType.BOOLEAN) {
                return TriState.of((Boolean) HANDLER.getPermission(player, node));
            } else {
                return TriState.DEFAULT;
            }
        });

        OptionRequestEvent.EVENT.register((source, perm) -> {
            if (HANDLER == null) {
                return Optional.empty();
            }

            var player = getPlayerFromSource(source);

            if (player == null) {
                return Optional.empty();
            }

            var node = PERMISSIONS.get(perm);

            if (node.getPermissionType() != PermissionType.BOOLEAN) {
                PermissionType type = node.getPermissionType();
                Function<Object, String> serializer = type.getSerializer();
                String s = serializer.apply(HANDLER.getPermission(player, node));
                return Optional.of(s);
            } else {
                return Optional.empty();
            }
        });
    }

    @Nullable
    public static ServerPlayer getPlayerFromSource(SharedSuggestionProvider provider) {
        return provider instanceof CommandSourceStack stack && stack.isPlayer() ? stack.getPlayer() : null;
    }

    public static void registerPermissionManager(PermissionHandler permissionHandler) {
        if (HANDLER != null) {
            PalladiumCore.LOGGER.info("Permission handler '" + HANDLER.getIdentifier() + "' was overridden by '" + permissionHandler.getIdentifier() + "'");
        } else {
            PalladiumCore.LOGGER.info("New permission handler set: " + permissionHandler.getIdentifier());
        }
        HANDLER = permissionHandler;
    }

    public static <T> IPermissionNode<T> registerPermission(ResourceLocation key, PermissionType<T> type, PermissionResolver<T> defaultResolver) {
        var node = new PermissionNodeImpl<>(key.getNamespace() + "." + key.getPath(), type, defaultResolver);
        PERMISSIONS.put(key.getNamespace() + "." + key.getPath(), node);
        return node;
    }

    public static <T> T getPermission(ServerPlayer player, IPermissionNode<T> node) {
        if (node.getPermissionType() == PermissionType.BOOLEAN) {
            Object defaultValue = node.getDefaultPermissionResolver().resolve(player, player.getUUID());
            Object result = Permissions.check(player.createCommandSourceStack(), node.getPermissionKey(), (Boolean) defaultValue);
            return (T) result;
        } else {
            var defaultValue = node.getPermissionType().getSerializer().apply(node.getDefaultPermissionResolver().resolve(player, player.getUUID()));
            var option = Options.get(player.createCommandSourceStack(), node.getPermissionKey(), defaultValue);
            return node.getPermissionType().getParser().apply(option);
        }
    }

    public static <T> T getOfflinePermission(UUID player, IPermissionNode<T> node) {
        // not supported I guess
        return node.getDefaultPermissionResolver().resolve(null, player);
    }
}
