package net.threetag.palladiumcore.registry.client.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings({"rawtypes", "UnnecessaryLocalVariable", "unchecked"})
@Environment(EnvType.CLIENT)
public class ParticleProviderRegistryImpl {

    private static final Map<Supplier<ParticleType<?>>, ParticleProvider<?>> PROVIDERS = new HashMap<>();
    private static final Map<Supplier<ParticleType<?>>, ParticleEngine.SpriteParticleRegistration<?>> SPRITES = new HashMap<>();

    public static <T extends ParticleOptions> void register(Supplier<ParticleType<T>> type, ParticleProvider<T> provider) {
        Supplier supplier = type;
        PROVIDERS.put(supplier, provider);
    }

    public static <T extends ParticleOptions> void register(Supplier<ParticleType<T>> type, ParticleEngine.SpriteParticleRegistration<T> registration) {
        Supplier supplier = type;
        SPRITES.put(supplier, registration);
    }

    public static void load() {
        for (Map.Entry<Supplier<ParticleType<?>>, ParticleProvider<?>> entry : PROVIDERS.entrySet()) {
            ParticleType type = entry.getKey().get();
            ParticleProvider provider = entry.getValue();
            Minecraft.getInstance().particleEngine.register(type, provider);
        }

        for (Map.Entry<Supplier<ParticleType<?>>, ParticleEngine.SpriteParticleRegistration<?>> entry : SPRITES.entrySet()) {
            ParticleType type = entry.getKey().get();
            ParticleEngine.SpriteParticleRegistration provider = entry.getValue();
            Minecraft.getInstance().particleEngine.register(type, provider);
        }
    }

}
