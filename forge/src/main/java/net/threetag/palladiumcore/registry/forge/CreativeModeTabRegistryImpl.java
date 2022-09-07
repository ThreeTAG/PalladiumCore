package net.threetag.palladiumcore.registry.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class CreativeModeTabRegistryImpl {

    public static CreativeModeTab create(ResourceLocation id, Supplier<ItemStack> supplier) {
        return new CreativeModeTab(String.format("%s.%s", id.getNamespace(), id.getPath())) {
            @Override
            @Nonnull
            public ItemStack makeIcon() {
                return supplier.get();
            }
        };
    }

}
