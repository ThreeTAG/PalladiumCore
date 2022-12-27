package net.threetag.palladiumcore.item;

import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;
import net.threetag.palladiumcore.registry.RegistrySupplier;

public class PalladiumRecordItem extends RecordItem {

    private final RegistrySupplier<SoundEvent> soundEvent;

    public PalladiumRecordItem(int i, RegistrySupplier<SoundEvent> soundEvent, Properties properties, int j) {
        super(i, null, properties, j);
        this.soundEvent = soundEvent;
        BY_NAME.remove(null);
    }

    @Override
    public SoundEvent getSound() {
        return this.soundEvent.get();
    }

    public static void setupEvents() {
        for (Item item : Registry.ITEM) {
            if(item instanceof PalladiumRecordItem recordItem) {
                RecordItem.BY_NAME.put(recordItem.soundEvent.get(), recordItem);
            }
        }
    }
}
