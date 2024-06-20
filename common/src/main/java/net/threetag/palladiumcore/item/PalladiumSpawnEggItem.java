package net.threetag.palladiumcore.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.threetag.palladiumcore.event.LifecycleEvents;
import net.threetag.palladiumcore.registry.client.ColorHandlerRegistry;
import net.threetag.palladiumcore.util.Platform;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class PalladiumSpawnEggItem extends SpawnEggItem {

    public static final List<PalladiumSpawnEggItem> MOD_EGGS = new ArrayList<>();
    private static final Map<EntityType<? extends Mob>, PalladiumSpawnEggItem> TYPE_MAP = new IdentityHashMap<>();
    private final Supplier<? extends EntityType<? extends Mob>> typeSupplier;

    public PalladiumSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props) {
        super(null, backgroundColor, highlightColor, props);
        this.typeSupplier = type;

        MOD_EGGS.add(this);

        if (Platform.isNeoForge()) {
            throw new IllegalStateException("This class should've been replaced on NeoForge!");
        }
    }

    @SuppressWarnings("ConstantValue")
    @Override
    public @NotNull EntityType<?> getType(ItemStack stack) {
        EntityType<?> type = super.getType(stack);
        return type != null ? type : typeSupplier.get();
    }

    @Nullable
    protected DispenseItemBehavior createDispenseBehavior() {
        return DEFAULT_DISPENSE_BEHAVIOR;
    }

    @Override
    public @NotNull FeatureFlagSet requiredFeatures() {
        return this.typeSupplier.get().requiredFeatures();
    }

    @Nullable
    public static SpawnEggItem fromEntityType(@Nullable EntityType<?> type) {
        SpawnEggItem ret = TYPE_MAP.get(type);
        return ret != null ? ret : SpawnEggItem.byId(type);
    }

    private static final DispenseItemBehavior DEFAULT_DISPENSE_BEHAVIOR = (source, stack) ->
    {
        Direction face = source.state().getValue(DispenserBlock.FACING);
        EntityType<?> type = ((SpawnEggItem) stack.getItem()).getType(stack);

        try {
            type.spawn(source.level(), stack, null, source.pos().relative(face), MobSpawnType.DISPENSER, face != Direction.UP, false);
        } catch (Exception exception) {
            DispenseItemBehavior.LOGGER.error("Error while dispensing spawn egg from dispenser at {}", source.pos(), exception);
            return ItemStack.EMPTY;
        }

        stack.shrink(1);
        source.level().gameEvent(GameEvent.ENTITY_PLACE, source.pos(), GameEvent.Context.of(source.state()));
        return stack;
    };

    public static void setupEvents() {
        LifecycleEvents.SETUP.register(() -> MOD_EGGS.forEach(egg ->
        {
            DispenseItemBehavior dispenseBehavior = egg.createDispenseBehavior();
            if (dispenseBehavior != null) {
                DispenserBlock.registerBehavior(egg, dispenseBehavior);
            }

            TYPE_MAP.put(egg.typeSupplier.get(), egg);
        }));
    }

    @Environment(EnvType.CLIENT)
    public static void colorHandlers() {
        for (PalladiumSpawnEggItem egg : MOD_EGGS) {
            ColorHandlerRegistry.registerItemColors((stack, layer) -> egg.getColor(layer), () -> egg);
        }
    }

}
