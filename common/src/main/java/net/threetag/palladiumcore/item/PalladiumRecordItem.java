package net.threetag.palladiumcore.item;

import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;
import net.threetag.palladiumcore.util.Platform;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class PalladiumRecordItem extends RecordItem {

    private final Supplier<SoundEvent> sound;

    public PalladiumRecordItem(int comparatorValue, Supplier<SoundEvent> soundEvent, Properties properties, int lengthInSeconds) {
        super(comparatorValue, checkPlatform(soundEvent).get(), properties, lengthInSeconds);
        this.sound = soundEvent;
        RecordItem.BY_NAME.remove(null);
    }

    private static <T> T checkPlatform(T obj) {
        if (Platform.isForge()) {
            throw new IllegalStateException("This class should've been replaced on Forge!");
        }

        return obj;
    }

    public static void registerRecords() {
        for (Item item : Registry.ITEM) {
            if (item instanceof PalladiumRecordItem recordItem) {
                RecordItem.BY_NAME.put(recordItem.sound.get(), recordItem);
            }
        }
    }

    @Override
    public @NotNull SoundEvent getSound() {
        return sound.get();
    }

    @Deprecated
    public static RecordItem create(int i, Supplier<SoundEvent> soundEvent, Item.Properties properties, int j) {
        return new PalladiumRecordItem(i, soundEvent, properties, j);
    }
}
