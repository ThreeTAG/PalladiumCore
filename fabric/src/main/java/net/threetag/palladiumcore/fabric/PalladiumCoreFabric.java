package net.threetag.palladiumcore.fabric;

import net.fabricmc.api.ModInitializer;
import net.threetag.palladiumcore.PalladiumCore;

public class PalladiumCoreFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        PalladiumCore.init();
    }

}
