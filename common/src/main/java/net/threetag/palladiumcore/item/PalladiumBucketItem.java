package net.threetag.palladiumcore.item;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.material.Fluid;
import net.threetag.palladiumcore.util.Platform;

import java.util.function.Supplier;

public class PalladiumBucketItem extends BucketItem {

    private final Fluid content;

    public PalladiumBucketItem(Supplier<? extends Fluid> content, Properties properties) {
        super(checkPlatform(content).get(), properties);
        this.content = content.get();
    }

    private static <T> T checkPlatform(T obj) {
        if (Platform.isForge()) {
            throw new IllegalStateException("This class should've been replaced on Forge!");
        }

        return obj;
    }

    public final Fluid getContainedFluid() {
        return this.content;
    }
}
