package net.threetag.palladiumcore.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;

import java.util.function.Function;

public class ReloadListenerRegistry {

    @ExpectPlatform
    public static void registerClientListener(ResourceLocation id, PreparableReloadListener listener) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerServerListener(ResourceLocation id, Function<HolderLookup.Provider, PreparableReloadListener> listener) {
        throw new AssertionError();
    }
}
