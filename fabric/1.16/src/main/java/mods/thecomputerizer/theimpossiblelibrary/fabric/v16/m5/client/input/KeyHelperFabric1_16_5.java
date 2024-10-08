package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.client.input;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.input.KeyHelper1_16_5;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class KeyHelperFabric1_16_5 extends KeyHelper1_16_5 {
    
    @Override public void register(KeyAPI<?> key) {
        KeyBindingHelper.registerKeyBinding(key.unwrap());
    }
}