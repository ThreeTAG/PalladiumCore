package net.threetag.palladiumcore.util.fabric;

import net.minecraft.world.entity.player.Player;

public class PlayerUtilImpl {

    public static void refreshDisplayName(Player player) {
        if (player instanceof RefreshableDisplayName name) {
            name.palladiumCore$refreshDisplayName();
        }
    }

    public interface RefreshableDisplayName {

        void palladiumCore$refreshDisplayName();

    }
}
