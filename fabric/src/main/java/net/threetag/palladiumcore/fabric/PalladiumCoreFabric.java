package net.threetag.palladiumcore.fabric;

import net.threetag.palladiumcore.PalladiumCore;
import net.fabricmc.api.ModInitializer;

public class PalladiumCoreFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        PalladiumCore.init();
    }
    
}
