package net.threetag.palladiumcore.mixin.fabric;

import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import net.threetag.palladiumcore.event.PlayerEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("ConstantConditions")
@Mixin(AnvilMenu.class)
public class AnvilMenuMixin {

    @Shadow
    private String itemName;

    @Shadow
    @Final
    private DataSlot cost;

    @Shadow
    private int repairItemCountCost;

    @Inject(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z", ordinal = 2),
            method = "createResult",
            cancellable = true)
    private void createResult(CallbackInfo ci) {
        AnvilMenu menu = (AnvilMenu) (Object) this;
        ItemStack left = menu.inputSlots.getItem(0);
        ItemStack right = menu.inputSlots.getItem(1);

        AtomicInteger cost = new AtomicInteger(left.getBaseRepairCost() + (right.isEmpty() ? 0 : right.getBaseRepairCost()));
        AtomicInteger materialCost = new AtomicInteger(0);
        AtomicReference<ItemStack> output = new AtomicReference<>(ItemStack.EMPTY);

        if (PlayerEvents.ANVIL_UPDATE.invoker().anvilUpdate(menu.player, left, right, this.itemName, cost, materialCost, output).cancelsEvent()) {
            ci.cancel();
            return;
        }

        if (output.get().isEmpty()) {
            return;
        }

        menu.resultSlots.setItem(0, output.get());
        this.cost.set(cost.get());
        this.repairItemCountCost = materialCost.get();
        ci.cancel();
    }

}
