package net.threetag.palladiumcore.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;

public class VillagerTradeRegistry {

    /**
     * Adds trades to the given {@link VillagerProfession}
     *
     * @param profession The {@link VillagerProfession} receiving the new trades
     * @param level      The required level of the villager to have this trade offer
     * @param trades     The trades that are to be added
     */
    @ExpectPlatform
    public static void registerForProfession(VillagerProfession profession, int level, VillagerTrades.ItemListing... trades) {
        throw new AssertionError();
    }

    /**
     * Adds trades to the wandering trader
     *
     * @param rare   Marks trades as rare
     * @param trades The trades that are to be added
     */
    @ExpectPlatform
    public static void registerForWanderingTrader(boolean rare, VillagerTrades.ItemListing... trades) {
        throw new AssertionError();
    }

}
