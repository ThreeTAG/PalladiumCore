package net.threetag.palladiumcore.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class CreativeModeTabRegistry {

    @ExpectPlatform
    public static CreativeModeTab create(ResourceLocation id, Supplier<ItemStack> icon) {
        throw new AssertionError();
    }
}
