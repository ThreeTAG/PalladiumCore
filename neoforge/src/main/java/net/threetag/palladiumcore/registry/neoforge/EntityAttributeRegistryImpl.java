package net.threetag.palladiumcore.registry.neoforge;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.threetag.palladiumcore.PalladiumCore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@EventBusSubscriber(modid = PalladiumCore.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class EntityAttributeRegistryImpl {

    private static final Map<Supplier<? extends EntityType<? extends LivingEntity>>, Supplier<AttributeSupplier.Builder>> ATTRIBUTES = new HashMap<>();
    private static final List<Modification> MODIFICATIONS = new ArrayList<>();

    public static void register(Supplier<? extends EntityType<? extends LivingEntity>> typeSupplier, Supplier<AttributeSupplier.Builder> builderSupplier) {
        ATTRIBUTES.put(typeSupplier, builderSupplier);
    }

    public static void registerModification(Supplier<EntityType<? extends LivingEntity>> typeSupplier, Holder<Attribute> attributeHolder, Double value) {
        MODIFICATIONS.add(new Modification(typeSupplier, attributeHolder, value));
    }

    @SubscribeEvent
    public static void onAttributeCreation(EntityAttributeCreationEvent e) {
        ATTRIBUTES.forEach((supplier, builderSupplier) -> e.put(supplier.get(), builderSupplier.get().build()));
    }

    @SubscribeEvent
    public static void onAttributeModification(EntityAttributeModificationEvent e) {
        for (Modification modification : MODIFICATIONS) {
            e.add(modification.typeSupplier.get(), modification.attributeHolder, modification.value == null ? modification.attributeHolder.value().getDefaultValue() : modification.value);
        }
    }

    public record Modification(Supplier<EntityType<? extends LivingEntity>> typeSupplier,
                               Holder<Attribute> attributeHolder,
                               Double value) {

    }
}
