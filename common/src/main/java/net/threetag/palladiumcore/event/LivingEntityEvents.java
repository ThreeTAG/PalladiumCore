package net.threetag.palladiumcore.event;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class LivingEntityEvents {

    public static final Event<Death> DEATH = new Event<>(Death.class, listeners -> (e, s) -> Event.result(listeners, death -> death.livingEntityDeath(e, s)));

    @FunctionalInterface
    public interface Death {

        EventResult livingEntityDeath(LivingEntity entity, DamageSource damageSource);

    }

}
