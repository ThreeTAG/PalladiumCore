package net.threetag.palladiumcore.mixin.neoforge;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.extensions.IItemExtension;
import net.threetag.palladiumcore.item.PalladiumItem;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PalladiumItem.class)
public interface PalladiumItemMixin extends IItemExtension {

    @Override
    @Nullable
    default EquipmentSlot getEquipmentSlot(ItemStack stack) {
        PalladiumItem item = (PalladiumItem) this;
        return item.getSlotForItem(stack);
    }
}
