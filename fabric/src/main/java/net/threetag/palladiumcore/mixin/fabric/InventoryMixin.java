package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.threetag.palladiumcore.item.PalladiumItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Inventory.class)
public class InventoryMixin {

    @Shadow @Final public NonNullList<ItemStack> armor;

    @Shadow @Final public Player player;

    @Inject(method = "tick", at = @At("RETURN"))
    private void tick(CallbackInfo ci) {
        this.armor.forEach(stack -> {
            if(stack.getItem() instanceof PalladiumItem palladiumItem) {
                palladiumItem.armorTick(stack, this.player.level(), this.player);
            }
        });
    }

}
