package net.threetag.palladiumcore.mixin.fabric;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AttributeSupplier.Builder.class)
public interface AttributeSupplierBuilderMixin {

    @Accessor
    ImmutableMap.Builder<Holder<Attribute>, AttributeInstance> getBuilder();

}
