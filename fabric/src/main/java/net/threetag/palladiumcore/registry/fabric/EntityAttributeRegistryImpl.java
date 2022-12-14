package net.threetag.palladiumcore.registry.fabric;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.threetag.palladiumcore.mixin.fabric.AttributeSupplierBuilderMixin;
import net.threetag.palladiumcore.mixin.fabric.AttributeSupplierMixin;

import java.util.*;
import java.util.function.Supplier;

public class EntityAttributeRegistryImpl {

    private static final Map<EntityType<? extends LivingEntity>, AttributeSupplier> MODIFIED = new HashMap<>();
    private static final List<Modification> MODIFICATIONS = new ArrayList<>();

    public static void register(Supplier<? extends EntityType<? extends LivingEntity>> type, Supplier<AttributeSupplier.Builder> attribute) {
        FabricDefaultAttributeRegistry.register(type.get(), attribute.get());
    }

    public static void registerModification(Supplier<EntityType<? extends LivingEntity>> typeSupplier, Supplier<Attribute> attributeSupplier, Double value) {
        MODIFICATIONS.add(new Modification(typeSupplier, attributeSupplier, value));
    }

    public static Map<EntityType<? extends LivingEntity>, AttributeSupplier> getAttributesView() {
        return Collections.unmodifiableMap(MODIFIED);
    }

    public static void modifyAttributes() {
        Map<EntityType<? extends LivingEntity>, AttributeSupplier.Builder> builders = new HashMap<>();
        for (Modification modification : MODIFICATIONS) {
            AttributeSupplier.Builder attributes = builders.computeIfAbsent(modification.typeSupplier.get(),
                    (type) -> new AttributeSupplier.Builder());
            attributes.add(modification.attributeSupplier.get(), modification.value == null ? modification.attributeSupplier.get().getDefaultValue() : modification.value);
        }

        builders.forEach((k, v) ->
        {
            AttributeSupplier supplier = DefaultAttributes.getSupplier(k);
            AttributeSupplier.Builder newBuilder = new AttributeSupplier.Builder();
            if (supplier != null) {
                ((AttributeSupplierBuilderMixin) newBuilder).getBuilder().putAll(((AttributeSupplierMixin) supplier).getInstances());
            }
            ((AttributeSupplierBuilderMixin) newBuilder).getBuilder().putAll(((AttributeSupplierBuilderMixin) v).getBuilder());
            MODIFIED.put(k, newBuilder.build());
        });
    }

    public record Modification(Supplier<EntityType<? extends LivingEntity>> typeSupplier,
                               Supplier<Attribute> attributeSupplier,
                               Double value) {

    }

}
