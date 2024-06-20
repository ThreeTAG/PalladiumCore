package net.threetag.palladiumcore.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;

import java.util.List;

public interface EntityEvents {

    /**
     * @see Tick#entityTick(Entity)
     */
    Event<Tick> TICK_PRE = new Event<>(Tick.class, listeners -> (e) -> {
        for (Tick listener : listeners) {
            listener.entityTick(e);
        }
    });

    /**
     * @see Tick#entityTick(Entity)
     */
    Event<Tick> TICK_POST = new Event<>(Tick.class, listeners -> (e) -> {
        for (Tick listener : listeners) {
            listener.entityTick(e);
        }
    });

    /**
     * @see JoinLevel#entityJoinLevel(Entity, Level)
     */
    Event<JoinLevel> JOIN_LEVEL = new Event<>(JoinLevel.class, listeners -> (e, l) -> {
        for (JoinLevel listener : listeners) {
            listener.entityJoinLevel(e, l);
        }
    });

    /**
     * @see LightningStrike#lightningStrike(List, LightningBolt)
     */
    Event<LightningStrike> LIGHTNING_STRIKE =  new Event<>(LightningStrike.class, listeners -> (e, l) -> {
        for (LightningStrike listener : listeners) {
            listener.lightningStrike(e, l);
        }
    });

    @FunctionalInterface
    interface Tick {

        /**
         * Called during every tick of an entity, duh
         *
         * @param entity The entity.
         */
        void entityTick(Entity entity);

    }

    @FunctionalInterface
    interface JoinLevel {

        /**
         * Called whenever an entity is added to a {@link Level}
         *
         * @param entity The newly spawned entity
         * @param level  The level the entity is spawned into
         */
        void entityJoinLevel(Entity entity, Level level);

    }

    @FunctionalInterface
    interface LightningStrike {

        /**
         * Called whenever a lightning strikes on entities
         *
         * @param entities      List of entities that have been struck
         * @param lightningBolt The actual lightning bolt
         */
        void lightningStrike(List<Entity> entities, LightningBolt lightningBolt);

    }

}
