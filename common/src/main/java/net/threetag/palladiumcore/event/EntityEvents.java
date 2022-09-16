package net.threetag.palladiumcore.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public interface EntityEvents {

    Event<JoinLevel> JOIN_LEVEL = new Event<>(JoinLevel.class, listeners -> (e, l) -> {
        for (JoinLevel listener : listeners) {
            listener.entityJoinLevel(e, l);
        }
    });

    @FunctionalInterface
    interface JoinLevel {

        void entityJoinLevel(Entity entity, Level level);

    }

}
