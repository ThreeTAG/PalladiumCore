package net.threetag.palladiumcore.registry.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemPropertyRegistry {

    @ExpectPlatform
    public static void register(Item item, ResourceLocation name, ClampedItemPropertyFunction property) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerGeneric(ResourceLocation name, ClampedItemPropertyFunction property) {
        throw new AssertionError();
    }

}
