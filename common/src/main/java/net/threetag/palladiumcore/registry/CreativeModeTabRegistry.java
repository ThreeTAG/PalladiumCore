package net.threetag.palladiumcore.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class CreativeModeTabRegistry {

    /**
     * Creates a {@link CreativeModeTab}
     *
     * @param id   ID for the tab, the translation key will consist out of "namespace"."path"
     * @param icon {@link Supplier} which returns an {@link ItemStack} for the icon of the tab
     * @return A new {@link CreativeModeTab}
     */
    @ExpectPlatform
    public static CreativeModeTab create(ResourceLocation id, Supplier<ItemStack> icon) {
        throw new AssertionError();
    }
}
