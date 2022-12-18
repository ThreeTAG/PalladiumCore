package net.threetag.palladiumcore.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.threetag.palladiumcore.PalladiumCore;
import net.threetag.palladiumcore.event.CommandEvents;
import net.threetag.palladiumcore.event.LifecycleEvents;
import net.threetag.palladiumcore.registry.RegistrySupplier;
import net.threetag.palladiumcore.registry.fabric.DeferredRegisterImpl;
import net.threetag.palladiumcore.registry.fabric.EntityAttributeRegistryImpl;
import net.threetag.palladiumcore.util.fabric.PlatformImpl;

public class PalladiumCoreFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        PalladiumCore.init();
        PlatformImpl.init();
        this.events();
    }

    private void events() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> CommandEvents.REGISTER.invoker().register(dispatcher, environment));

        ServerLifecycleEvents.SERVER_STARTING.register(server -> LifecycleEvents.SERVER_ABOUT_TO_START.invoker().server(server));
        ServerLifecycleEvents.SERVER_STARTED.register(server -> LifecycleEvents.SERVER_STARTED.invoker().server(server));
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> LifecycleEvents.SERVER_STOPPING.invoker().server(server));
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> LifecycleEvents.SERVER_STOPPED.invoker().server(server));



        LifecycleEvents.SETUP.register(() -> {
            EntityAttributeRegistryImpl.modifyAttributes();

            for (RegistrySupplier<PoiType> supplier : DeferredRegisterImpl.POI_TYPES_TO_FIX) {
                var key = ResourceKey.create(Registry.POINT_OF_INTEREST_TYPE_REGISTRY, supplier.getId());
                PoiTypes.registerBlockStates(Registry.POINT_OF_INTEREST_TYPE.getHolderOrThrow(key));
                PoiTypes.ALL_STATES.addAll(supplier.get().matchingStates());
            }
        });
    }

}
