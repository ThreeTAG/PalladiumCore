package net.threetag.palladiumcore.registry.client.forge;

import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.threetag.palladiumcore.PalladiumCore;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings({"rawtypes", "unchecked"})
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = PalladiumCore.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleProviderRegistryImpl {

    private static final Map<Supplier<ParticleType<?>>, ParticleProvider<?>> PROVIDERS = new HashMap<>();
    private static final Map<Supplier<ParticleType<?>>, ParticleEngine.SpriteParticleRegistration<?>> SPRITES = new HashMap<>();

    public static <T extends ParticleOptions> void register(Supplier<ParticleType<T>> type, ParticleProvider<T> provider) {
        throw new AssertionError();
    }

    public static  <T extends ParticleOptions> void register(Supplier<ParticleType<T>> type, ParticleEngine.SpriteParticleRegistration<T> registration) {
        throw new AssertionError();
    }

    @SubscribeEvent
    public static void onParticleProviderRegistration(RegisterParticleProvidersEvent e) {
        for (Map.Entry<Supplier<ParticleType<?>>, ParticleProvider<?>> entry : PROVIDERS.entrySet()) {
            ParticleType type = entry.getKey().get();
            ParticleProvider provider = entry.getValue();
            e.register(type, provider);
        }

        for (Map.Entry<Supplier<ParticleType<?>>, ParticleEngine.SpriteParticleRegistration<?>> entry : SPRITES.entrySet()) {
            ParticleType type = entry.getKey().get();
            ParticleEngine.SpriteParticleRegistration provider = entry.getValue();
            e.register(type, provider);
        }
    }

}
