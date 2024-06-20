package net.threetag.palladiumcore.mixin.neoforge;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import net.threetag.palladiumcore.network.ExtendedEntitySpawnData;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ExtendedEntitySpawnData.class)
public interface ExtendedEntitySpawnDataMixin extends IEntityWithComplexSpawn {

    @Override
    default void writeSpawnData(RegistryFriendlyByteBuf buf) {
        ((ExtendedEntitySpawnData) this).saveAdditionalSpawnData(buf);
    }

    @Override
    default void readSpawnData(RegistryFriendlyByteBuf buf) {
        ((ExtendedEntitySpawnData) this).loadAdditionalSpawnData(buf);
    }

}
