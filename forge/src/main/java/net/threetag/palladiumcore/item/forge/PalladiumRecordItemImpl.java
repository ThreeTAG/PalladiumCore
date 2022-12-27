package net.threetag.palladiumcore.item.forge;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;

import java.util.function.Supplier;

public class PalladiumRecordItemImpl {

    public static RecordItem create(int analogOutput, Supplier<SoundEvent> soundEvent, Item.Properties properties, int lengthInSeconds) {
        return new RecordItem(analogOutput, soundEvent, properties, lengthInSeconds);
    }

}
