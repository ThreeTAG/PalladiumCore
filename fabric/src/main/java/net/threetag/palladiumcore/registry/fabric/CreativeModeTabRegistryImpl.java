package net.threetag.palladiumcore.registry.fabric;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class CreativeModeTabRegistryImpl {

    public static CreativeModeTab create(ResourceLocation id, Supplier<ItemStack> icon) {
        return FabricItemGroupBuilder.build(id, icon);
    }

}
