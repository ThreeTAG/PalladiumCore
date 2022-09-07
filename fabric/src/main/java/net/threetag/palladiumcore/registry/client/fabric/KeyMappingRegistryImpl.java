package net.threetag.palladiumcore.registry.client.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

@Environment(EnvType.CLIENT)
public class KeyMappingRegistryImpl {

    public static void register(KeyMapping mapping) {
        KeyBindingHelper.registerKeyBinding(mapping);
    }

}
