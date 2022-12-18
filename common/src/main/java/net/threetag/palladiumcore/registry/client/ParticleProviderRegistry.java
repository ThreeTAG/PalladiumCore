package net.threetag.palladiumcore.registry.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

import java.util.function.Supplier;

public class ParticleProviderRegistry {

    @ExpectPlatform
    public static <T extends ParticleOptions> void register(Supplier<ParticleType<T>> type, ParticleProvider<T> provider) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends ParticleOptions> void register(Supplier<ParticleType<T>> type, ParticleEngine.SpriteParticleRegistration<T> registration) {
        throw new AssertionError();
    }

}
