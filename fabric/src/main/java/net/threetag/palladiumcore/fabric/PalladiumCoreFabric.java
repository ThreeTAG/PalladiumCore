package net.threetag.palladiumcore.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.threetag.palladiumcore.PalladiumCore;
import net.threetag.palladiumcore.event.ChatEvents;
import net.threetag.palladiumcore.event.CommandEvents;
import net.threetag.palladiumcore.event.LifecycleEvents;
import net.threetag.palladiumcore.item.PalladiumRecordItem;
import net.threetag.palladiumcore.item.PalladiumSpawnEggItem;
import net.threetag.palladiumcore.registry.DeferredRegister;
import net.threetag.palladiumcore.registry.RegistrySupplier;
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
        PalladiumSpawnEggItem.setupEvents();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> CommandEvents.REGISTER.invoker().register(dispatcher, environment));

        ServerLifecycleEvents.SERVER_STARTING.register(server -> LifecycleEvents.SERVER_ABOUT_TO_START.invoker().server(server));
        ServerLifecycleEvents.SERVER_STARTED.register(server -> LifecycleEvents.SERVER_STARTED.invoker().server(server));
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> LifecycleEvents.SERVER_STOPPING.invoker().server(server));
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> LifecycleEvents.SERVER_STOPPED.invoker().server(server));

        ServerMessageEvents.ALLOW_CHAT_MESSAGE.register((message, sender, params) -> !ChatEvents.SERVER_SUBMITTED.invoker().chatMessageSubmitted(sender, message.signedContent().plain(), message.signedContent().decorated()).cancelsEvent());

        LifecycleEvents.SETUP.register(() -> {
            EntityAttributeRegistryImpl.modifyAttributes();
            PalladiumRecordItem.registerRecords();

            for (RegistrySupplier<PoiType> supplier : DeferredRegister.POI_TYPES_TO_FIX) {
                var key = ResourceKey.create(Registry.POINT_OF_INTEREST_TYPE_REGISTRY, supplier.getId());
                PoiTypes.registerBlockStates(Registry.POINT_OF_INTEREST_TYPE.getHolderOrThrow(key));
                PoiTypes.ALL_STATES.addAll(supplier.get().matchingStates());
            }
        });
    }

}
