package net.threetag.palladiumcore.event;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public interface LivingEntityEvents {

    /**
     * @see Death#livingEntityDeath(LivingEntity, DamageSource)
     */
    Event<Death> DEATH = new Event<>(Death.class, listeners -> (e, s) -> Event.result(listeners, death -> death.livingEntityDeath(e, s)));

    /**
     * @see IncomingDamage#livingIncomingDamage(LivingEntity, DamageSource, AtomicReference)
     */
    Event<IncomingDamage> INCOMING_DAMAGE = new Event<>(IncomingDamage.class, listeners -> (e, s, a) -> Event.result(listeners, hurt -> hurt.livingIncomingDamage(e, s, a)));

    /**
     * @see DamagePost#livingDamagePost(LivingEntity, DamageSource, float)
     */
    Event<DamagePost> DAMAGE_POST = new Event<>(DamagePost.class, listeners -> (e, d, a) -> {
        for (DamagePost listener : listeners) {
            listener.livingDamagePost(e, d, a);
        }
    });

    /**
     * Called when a player starts using an item, usually when holding rightclick with it
     */
    Event<ItemUse> ITEM_USE_START = new Event<>(ItemUse.class, listeners -> (e, s, d) -> Event.result(listeners, hurt -> hurt.livingEntityItemUse(e, s, d)));

    /**
     * Called every tick during the usage of an item. Cancelling the event or setting the duration to 0 will make the player stop using the item
     */
    Event<ItemUse> ITEM_USE_TICK = new Event<>(ItemUse.class, listeners -> (e, s, d) -> Event.result(listeners, hurt -> hurt.livingEntityItemUse(e, s, d)));

    /**
     * Called when a player stops using an item without the use duration timing out (e.g. stop eating halfway through)
     */
    Event<ItemUse> ITEM_USE_STOP = new Event<>(ItemUse.class, listeners -> (e, s, d) -> Event.result(listeners, hurt -> hurt.livingEntityItemUse(e, s, d)));

    /**
     * Fired after an item has fully finished being used
     */
    Event<ItemUseFinish> ITEM_USE_FINISH = new Event<>(ItemUseFinish.class, listeners -> (e, s, d) -> {
        for (ItemUseFinish listener : listeners) {
            listener.livingEntityItemUseFinish(e, s, d);
        }
    });

    /**
     * @see Jump#livingEntityJump(LivingEntity)
     */
    Event<Jump> JUMP = new Event<>(Jump.class, listeners -> (e) -> {
        for (Jump listener : listeners) {
            listener.livingEntityJump(e);
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
    interface IncomingDamage {

        /**
         * Called every time before an Entity is hurt
         *
         * @param entity       The entity that is attacked
         * @param damageSource The {@link DamageSource} of the attack
         * @param amount       The amount of damage dealt to the entity
         * @return An {@link EventResult} determining whether to cancel the attacking process
         */
        EventResult livingIncomingDamage(LivingEntity entity, DamageSource damageSource, AtomicReference<Float> amount);

    }

    @FunctionalInterface
    interface DamagePost {

        /**
         * Called every time when an Entity has been hurt
         *
         * @param entity       The entity that was hurt
         * @param damageSource The {@link DamageSource} that caused the entity to be hurt
         * @param amount       The amount of damage dealt to the hurt entity
         */
        void livingDamagePost(LivingEntity entity, DamageSource damageSource, float amount);

    }

    @FunctionalInterface
    interface ItemUse {

        EventResult livingEntityItemUse(LivingEntity entity, @NotNull ItemStack stack, AtomicInteger duration);

    }

    @FunctionalInterface
    interface ItemUseFinish {

        void livingEntityItemUseFinish(LivingEntity entity, @NotNull ItemStack stack, AtomicInteger duration);

    }

    @FunctionalInterface
    interface Jump {

        /**
         * Called when an entity jumps
         *
         * @param entity The jumping entity
         */
        void livingEntityJump(LivingEntity entity);

    }


}
