package net.threetag.palladiumcore.registry.fabric;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;

import java.util.Collections;

public class VillagerTradeRegistryImpl {

    public static void registerForProfession(VillagerProfession profession, int level, VillagerTrades.ItemListing... trades) {
        TradeOfferHelper.registerVillagerOffers(profession, level, c -> Collections.addAll(c, trades));
    }

    public static void registerForWanderingTrader(boolean rare, VillagerTrades.ItemListing... trades) {
        TradeOfferHelper.registerWanderingTraderOffers(rare ? 2 : 1, c -> Collections.addAll(c, trades));
    }
}
