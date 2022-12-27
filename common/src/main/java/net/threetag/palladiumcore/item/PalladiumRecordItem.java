package net.threetag.palladiumcore.item;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;

import java.util.function.Supplier;

public class PalladiumRecordItem {

    @ExpectPlatform
    public static RecordItem create(int i, Supplier<SoundEvent> soundEvent, Item.Properties properties, int j) {
        throw new AssertionError();
    }

}
