package net.threetag.palladiumcore.registry.forge;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.threetag.palladiumcore.PalladiumCore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = PalladiumCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityAttributeRegistryImpl {

    private static final Map<Supplier<? extends EntityType<? extends LivingEntity>>, Supplier<AttributeSupplier.Builder>> ATTRIBUTES = new HashMap<>();
    private static final List<Modification> MODIFICATIONS = new ArrayList<>();

    public static void register(Supplier<? extends EntityType<? extends LivingEntity>> typeSupplier, Supplier<AttributeSupplier.Builder> builderSupplier) {
        ATTRIBUTES.put(typeSupplier, builderSupplier);
    }

    public static void registerModification(Supplier<EntityType<? extends LivingEntity>> typeSupplier, Supplier<Attribute> attributeSupplier, Double value) {
        MODIFICATIONS.add(new Modification(typeSupplier, attributeSupplier, value));
    }

    @SubscribeEvent
    public static void onAttributeModification(EntityAttributeModificationEvent e) {
        for (Modification modification : MODIFICATIONS) {
            e.add(modification.typeSupplier.get(), modification.attributeSupplier.get(), modification.value == null ? modification.attributeSupplier.get().getDefaultValue() : modification.value);
        }
    }

    public record Modification(Supplier<EntityType<? extends LivingEntity>> typeSupplier,
                               Supplier<Attribute> attributeSupplier,
                               Double value) {

    }
}
