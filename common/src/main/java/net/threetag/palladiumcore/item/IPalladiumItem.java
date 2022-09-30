package net.threetag.palladiumcore.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IPalladiumItem {

    /**
     * Called every tick when wearing this item as armor
     */
    default void armorTick(ItemStack stack, Level level, Player player) {
    }

    /**
     * Using this makes the item wearable in the returned slot
     *
     * @param stack     Worn ItemStack
     * @return The armor slot the item can be worn in
     */
    default EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return null;
    }

}
