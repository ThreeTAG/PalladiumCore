package net.threetag.palladiumcore.registry.client.fabric;

import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemPropertyRegistryImpl {

    public static void register(Item item, ResourceLocation name, ClampedItemPropertyFunction property) {
        ItemProperties.register(item, name, property);
    }

    public static void registerGeneric(ResourceLocation name, ClampedItemPropertyFunction property) {
        ItemProperties.registerGeneric(name, property);
    }
}
