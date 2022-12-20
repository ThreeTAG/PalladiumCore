package net.threetag.palladiumcore.registry.client.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class ParticleProviderRegistryImpl {

    public static <T extends ParticleOptions> void register(Supplier<ParticleType<T>> type, ParticleProvider<T> provider) {
        ParticleFactoryRegistry.getInstance().register(type.get(), provider);
    }

    public static <T extends ParticleOptions> void register(Supplier<ParticleType<T>> type, ParticleEngine.SpriteParticleRegistration<T> registration) {
        ParticleFactoryRegistry.getInstance().register(type.get(), registration::create);
    }

}
