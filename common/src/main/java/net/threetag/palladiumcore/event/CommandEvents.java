package net.threetag.palladiumcore.event;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class CommandEvents {

    public static final Event<Register> REGISTER = new Event<>(Register.class, listeners -> (d, s) -> {
        for (Register listener : listeners) {
            listener.register(d, s);
        }
    });

    @FunctionalInterface
    public interface Register {

        void register(CommandDispatcher<CommandSourceStack> dispatcher, Commands.CommandSelection selection);

    }

}
