package net.threetag.palladiumcore.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;

public class ReloadListenerRegistry {

    @ExpectPlatform
    public static void register(PackType packType, ResourceLocation id, PreparableReloadListener listener) {
        throw new AssertionError();
    }
}
