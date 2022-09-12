package net.threetag.palladiumcore.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.threetag.palladiumcore.PalladiumCore;
import net.threetag.palladiumcore.event.CommandEvents;

public class PalladiumCoreFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        PalladiumCore.init();
        this.events();
    }

    private void events() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> CommandEvents.REGISTER.invoker().register(dispatcher, environment));
    }

}
