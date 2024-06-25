package net.threetag.palladiumcore.item;

import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class SimpleArmorMaterial implements net.minecraft.world.item.ArmorMaterial {

    private static final EnumMap<ArmorItem.Type, Integer> HEALTH_FUNCTION_FOR_TYPE = (EnumMap) Util.make(new EnumMap(ArmorItem.Type.class), (enumMap) -> {
        enumMap.put(ArmorItem.Type.BOOTS, 13);
        enumMap.put(ArmorItem.Type.LEGGINGS, 15);
        enumMap.put(ArmorItem.Type.CHESTPLATE, 16);
        enumMap.put(ArmorItem.Type.HELMET, 11);
    });
    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> slotProtections;
    private final int enchantmentValue;
    private final Supplier<SoundEvent> soundEvent;
    private final float toughness;
    private final float knockBackResistance;
    private final Supplier<Ingredient> repairMaterial;

    public SimpleArmorMaterial(String nameIn, int durabilityMultiplier, EnumMap<ArmorItem.Type, Integer> slotProtections, int enchantmentValue, Supplier<SoundEvent> equipSoundIn, float toughness, float knockBackResistance, Supplier<Ingredient> repairMaterialSupplier) {
        this.name = nameIn;
        this.durabilityMultiplier = durabilityMultiplier;
        this.slotProtections = slotProtections;
        this.enchantmentValue = enchantmentValue;
        this.soundEvent = equipSoundIn;
        this.toughness = toughness;
        this.knockBackResistance = knockBackResistance;
        this.repairMaterial = repairMaterialSupplier;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return HEALTH_FUNCTION_FOR_TYPE.get(type) * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return this.slotProtections.get(type);
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.soundEvent.get();
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockBackResistance;
    }
}
