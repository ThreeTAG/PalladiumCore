package net.threetag.palladiumcore.registry.fabric;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;

public class ReloadListenerRegistryImpl {

    public static void registerClientListener(ResourceLocation id, PreparableReloadListener listener) {
        var fabricListener = new Wrapper(id, listener);
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(fabricListener);
    }

    public static void registerServerListener(ResourceLocation id, Function<HolderLookup.Provider, PreparableReloadListener> listener) {
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(id, provider -> new Wrapper(id, listener.apply(provider)));
    }

    private record Wrapper(ResourceLocation id,
                           PreparableReloadListener listener) implements IdentifiableResourceReloadListener {

        @Override
            public ResourceLocation getFabricId() {
                return this.id;
            }

            @Override
            public String getName() {
                return this.listener.getName();
            }

            @Override
            public CompletableFuture<Void> reload(PreparationBarrier preparationBarrier, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
                return listener.reload(preparationBarrier, resourceManager, preparationsProfiler, reloadProfiler, backgroundExecutor, gameExecutor);
            }
        }

}
