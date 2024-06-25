package net.threetag.palladiumcore.mixin.forge;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.threetag.palladiumcore.network.ExtendedEntitySpawnData;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ExtendedEntitySpawnData.class)
public interface ExtendedEntitySpawnDataMixin extends IEntityAdditionalSpawnData {

    @Override
    default void writeSpawnData(FriendlyByteBuf buf) {
        ((ExtendedEntitySpawnData) this).saveAdditionalSpawnData(buf);
    }

    @Override
    default void readSpawnData(FriendlyByteBuf buf) {
        ((ExtendedEntitySpawnData) this).loadAdditionalSpawnData(buf);
    }

}
