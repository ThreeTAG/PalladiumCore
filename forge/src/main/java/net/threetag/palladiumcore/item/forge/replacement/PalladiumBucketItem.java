package net.threetag.palladiumcore.item.forge.replacement;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

import java.util.function.Supplier;

public class PalladiumBucketItem extends BucketItem {

    public PalladiumBucketItem(Supplier<? extends Fluid> supplier, Properties builder) {
        super(supplier, builder);
    }

    public final Fluid getContainedFluid() {
        return getFluid();
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        return this.getClass() == PalladiumBucketItem.class ? new FluidBucketWrapper(stack) : super.initCapabilities(stack, nbt);
    }

}
