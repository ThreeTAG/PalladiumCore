package net.threetag.palladiumcore.item.neoforge.replacement;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.capability.wrappers.FluidBucketWrapper;
import net.threetag.palladiumcore.PalladiumCore;
import net.threetag.palladiumcore.neoforge.PalladiumCoreNeoForge;

import java.util.function.Supplier;

public class PalladiumBucketItem extends BucketItem {

    public PalladiumBucketItem(Supplier<? extends Fluid> supplier, Properties builder) {
        super(supplier.get(), builder);
        PalladiumCoreNeoForge.whenModBusAvailable(PalladiumCore.MOD_ID, bus -> {
            bus.<RegisterCapabilitiesEvent>addListener(e -> e.registerItem(Capabilities.FluidHandler.ITEM, (stack, ctx) -> new FluidBucketWrapper(stack), this));
        });
    }

    public final Fluid getContainedFluid() {
        return this.content;
    }
}
