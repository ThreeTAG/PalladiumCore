package net.threetag.palladiumcore.registry.forge;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.threetag.palladiumcore.PalladiumCore;

import java.util.*;

@Mod.EventBusSubscriber(modid = PalladiumCore.MOD_ID)
public class VillagerTradeRegistryImpl {

    private static final Map<VillagerProfession, Int2ObjectMap<List<VillagerTrades.ItemListing>>> TRADES = new HashMap<>();
    private static final List<VillagerTrades.ItemListing> WANDERER = new ArrayList<>();
    private static final List<VillagerTrades.ItemListing> WANDERER_RARE = new ArrayList<>();

    public static void registerForProfession(VillagerProfession profession, int level, VillagerTrades.ItemListing... trades) {
        var map = TRADES.computeIfAbsent(profession, prof -> new Int2ObjectOpenHashMap<>());
        var tradesList = map.computeIfAbsent(level, l -> new ArrayList<>());
        Collections.addAll(tradesList, trades);
    }

    public static void registerForWanderingTrader(boolean rare, VillagerTrades.ItemListing... trades) {
        if (rare) {
            Collections.addAll(WANDERER_RARE, trades);
        } else {
            Collections.addAll(WANDERER, trades);
        }
    }

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent e) {
        var map = TRADES.get(e.getType());

        if (map != null) {
            for (Int2ObjectMap.Entry<List<VillagerTrades.ItemListing>> listEntry : map.int2ObjectEntrySet()) {
                e.getTrades().computeIfAbsent(listEntry.getIntKey(), l -> new ArrayList<>()).addAll(listEntry.getValue());
            }
        }
    }

    @SubscribeEvent
    public static void onVillagerTrades(WandererTradesEvent e) {
        e.getGenericTrades().addAll(WANDERER);
        e.getRareTrades().addAll(WANDERER_RARE);
    }

}
