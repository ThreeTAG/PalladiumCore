package net.threetag.palladiumcore.event;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public interface CommandEvents {

    /**
     * @see Register#register(CommandDispatcher, CommandBuildContext, Commands.CommandSelection)
     */
    Event<Register> REGISTER = new Event<>(Register.class, listeners -> (d, c, s) -> {
        for (Register listener : listeners) {
            listener.register(d, c, s);
        }
    });

    @FunctionalInterface
    interface Register {

        /**
         * Used to register custom commands. Called after the server has registered its own.
         *
         * @param dispatcher Command dispatcher used for registering commands
         * @param context    Returns the context to build the commands for.
         * @param selection  The environment the command is being registered for
         */
        void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context, Commands.CommandSelection selection);

    }

}
