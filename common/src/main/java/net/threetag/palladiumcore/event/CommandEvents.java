package net.threetag.palladiumcore.event;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public interface CommandEvents {

    /**
     * @see Register#register(CommandDispatcher, Commands.CommandSelection)
     */
    Event<Register> REGISTER = new Event<>(Register.class, listeners -> (d, s) -> {
        for (Register listener : listeners) {
            listener.register(d, s);
        }
    });

    @FunctionalInterface
    interface Register {

        /**
         * Used to register custom commands. Called after the server has registered its own.
         *
         * @param dispatcher Command dispatcher used for registering commands
         * @param selection  The environment the command is being registered for
         */
        void register(CommandDispatcher<CommandSourceStack> dispatcher, Commands.CommandSelection selection);

    }

}
