package net.threetag.palladiumcore.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public interface EntityEvents {

    /**
     * @see JoinLevel#entityJoinLevel(Entity, Level)
     */
    Event<JoinLevel> JOIN_LEVEL = new Event<>(JoinLevel.class, listeners -> (e, l) -> {
        for (JoinLevel listener : listeners) {
            listener.entityJoinLevel(e, l);
        }
    });

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

}
