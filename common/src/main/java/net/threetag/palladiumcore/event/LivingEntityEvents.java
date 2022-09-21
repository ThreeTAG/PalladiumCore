package net.threetag.palladiumcore.event;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public interface LivingEntityEvents {

    /**
     * @see Death#livingEntityDeath(LivingEntity, DamageSource)
     */
    Event<Death> DEATH = new Event<>(Death.class, listeners -> (e, s) -> Event.result(listeners, death -> death.livingEntityDeath(e, s)));

    /**
     * @see Hurt#livingEntityHurt(LivingEntity, DamageSource, float)
     */
    Event<Hurt> HURT = new Event<>(Hurt.class, listeners -> (e, s, a) -> Event.result(listeners, hurt -> hurt.livingEntityHurt(e, s, a)));

    /**
     * @see Tick#livingEntityTick(LivingEntity)
     */
    Event<Tick> TICK = new Event<>(Tick.class, listeners -> (e) -> {
        for (Tick listener : listeners) {
            listener.livingEntityTick(e);
        }
    });

    @FunctionalInterface
    interface Death {

        /**
         * Called whenever a living entity dies.
         *
         * @param entity       The dying entity
         * @param damageSource The {@link DamageSource} that was responsible for the entity's death
         * @return An {@link EventResult} determining whether to cancel the dying process
         */
        EventResult livingEntityDeath(LivingEntity entity, DamageSource damageSource);

    }

    @FunctionalInterface
    interface Hurt {

        /**
         * Called every time when an Entity is set to be hurt.
         *
         * @param entity       The entity that is about to be hurt
         * @param damageSource The {@link DamageSource} that caused the entity to be hurt
         * @param amount       The amount of damage dealt to the hurt entity
         * @return An {@link EventResult} determining whether to cancel the hurting process
         */
        EventResult livingEntityHurt(LivingEntity entity, DamageSource damageSource, float amount);

    }

    @FunctionalInterface
    interface Tick {

        /**
         * Called during every tick of a living entity, duh
         *
         * @param entity The entity.
         */
        void livingEntityTick(LivingEntity entity);

    }

}
