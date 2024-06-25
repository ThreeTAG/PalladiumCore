package net.threetag.palladiumcore.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.function.Supplier;

public class EntityAttributeRegistry {

    @ExpectPlatform
    public static void register(Supplier<? extends EntityType<? extends LivingEntity>> typeSupplier, Supplier<AttributeSupplier.Builder> builderSupplier) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerModification(Supplier<EntityType<? extends LivingEntity>> typeSupplier, Supplier<Attribute> attributeSupplier, Double value) {
        throw new AssertionError();
    }

    public static void registerModification(Supplier<EntityType<? extends LivingEntity>> typeSupplier, Supplier<Attribute> attributeSupplier) {
        registerModification(typeSupplier, attributeSupplier, null);
    }

}
