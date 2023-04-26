package net.threetag.palladiumcore.item.fabric;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;

import java.util.function.Supplier;

public class PalladiumRecordItemImpl extends RecordItem {

    private final Supplier<SoundEvent> soundEvent;

    private PalladiumRecordItemImpl(int analogOutput, Supplier<SoundEvent> soundEvent, Properties properties, int lengthInSeconds) {
        super(analogOutput, null, properties, lengthInSeconds);
        this.soundEvent = soundEvent;
        BY_NAME.remove(null);
    }

    public static RecordItem create(int analogOutput, Supplier<SoundEvent> soundEvent, Item.Properties properties, int lengthInSeconds) {
        return new PalladiumRecordItemImpl(analogOutput, soundEvent, properties, lengthInSeconds);
    }

    @Override
    public SoundEvent getSound() {
        return this.soundEvent.get();
    }

    public static void setupEvents() {
        for (Item item : BuiltInRegistries.ITEM) {
            if(item instanceof PalladiumRecordItemImpl recordItem) {
                RecordItem.BY_NAME.put(recordItem.soundEvent.get(), recordItem);
            }
        }
    }
}
