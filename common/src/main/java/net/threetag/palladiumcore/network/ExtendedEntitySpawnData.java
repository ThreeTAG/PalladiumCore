package net.threetag.palladiumcore.network;

import net.minecraft.network.FriendlyByteBuf;

public interface ExtendedEntitySpawnData {

    void saveAdditionalSpawnData(FriendlyByteBuf buf);

    void loadAdditionalSpawnData(FriendlyByteBuf buf);

}