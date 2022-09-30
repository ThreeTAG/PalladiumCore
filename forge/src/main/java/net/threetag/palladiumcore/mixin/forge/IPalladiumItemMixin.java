package net.threetag.palladiumcore.mixin.forge;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeItem;
import net.threetag.palladiumcore.item.IPalladiumItem;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(IPalladiumItem.class)
public interface IPalladiumItemMixin extends IForgeItem {

    @Override
    default void onArmorTick(ItemStack stack, Level level, Player player) {
        IPalladiumItem item = (IPalladiumItem) this;
        item.armorTick(stack, level, player);
    }

    @Override
    @Nullable
    default EquipmentSlot getEquipmentSlot(ItemStack stack) {
        IPalladiumItem item = (IPalladiumItem) this;
        return item.getEquipmentSlot(stack);
    }
}
