package net.threetag.palladiumcore.permission.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.permission.PermissionAPI;
import net.minecraftforge.server.permission.events.PermissionGatherEvent;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import net.minecraftforge.server.permission.nodes.PermissionTypes;
import net.threetag.palladiumcore.PalladiumCore;
import net.threetag.palladiumcore.permission.IPermissionNode;
import net.threetag.palladiumcore.permission.PermissionHandler;
import net.threetag.palladiumcore.permission.PermissionResolver;
import net.threetag.palladiumcore.permission.PermissionType;

import java.util.*;

@SuppressWarnings("unchecked")
@Mod.EventBusSubscriber(modid = PalladiumCore.MOD_ID)
public class PermissionManagerImpl {

    private static final List<PermissionHandler> HANDLERS = new ArrayList<>();
    static final Map<PermissionNode<?>, IPermissionNode<?>> PERMISSIONS = new HashMap<>();

    public static void registerPermissionManager(PermissionHandler permissionHandler) {
        HANDLERS.add(permissionHandler);
    }

    public static <T> IPermissionNode<T> registerPermission(ResourceLocation key, PermissionType<T> type, PermissionResolver<T> defaultResolver) {
        net.minecraftforge.server.permission.nodes.PermissionType<T> type1 = (net.minecraftforge.server.permission.nodes.PermissionType<T>) PermissionTypes.getTypeByName(type.getName());
        PermissionNode.PermissionResolver<T> resolver = (arg, uUID, permissionDynamicContexts) -> defaultResolver.resolve(arg, uUID);
        PermissionNode<T> node = new PermissionNode<>(key, type1, resolver);
        WrappedPermissionNode<T> wrapped = new WrappedPermissionNode<>(node);
        PERMISSIONS.put(node, wrapped);
        return wrapped;
    }

    public static <T> T getPermission(ServerPlayer player, IPermissionNode<T> node) {
        return PermissionAPI.getPermission(player, ((WrappedPermissionNode<T>) node).getForgeNode());
    }

    public static <T> T getOfflinePermission(UUID player, IPermissionNode<T> node) {
        return PermissionAPI.getOfflinePermission(player, ((WrappedPermissionNode<T>) node).getForgeNode());
    }

    @SubscribeEvent
    public static void onRegisterHandler(PermissionGatherEvent.Handler e) {
        for (PermissionHandler handler : HANDLERS) {
            e.addPermissionHandler(handler.getIdentifier(), collection -> new WrappedPermissionHandler(handler, collection));
        }
    }

    @SubscribeEvent
    public static void onRegisterPermissions(PermissionGatherEvent.Nodes e) {
        e.addNodes(PERMISSIONS.keySet());
    }

}
