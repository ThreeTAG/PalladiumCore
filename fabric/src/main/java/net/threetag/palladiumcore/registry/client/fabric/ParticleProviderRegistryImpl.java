package net.threetag.palladiumcore.registry.client.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class ParticleProviderRegistryImpl {

    public static <T extends ParticleOptions> void register(Supplier<ParticleType<T>> type, ParticleProvider<T> provider) {
        Minecraft.getInstance().particleEngine.register(type.get(), provider);
    }

    public static <T extends ParticleOptions> void register(Supplier<ParticleType<T>> type, ParticleEngine.SpriteParticleRegistration<T> registration) {
        Minecraft.getInstance().particleEngine.register(type.get(), registration);
    }
}
