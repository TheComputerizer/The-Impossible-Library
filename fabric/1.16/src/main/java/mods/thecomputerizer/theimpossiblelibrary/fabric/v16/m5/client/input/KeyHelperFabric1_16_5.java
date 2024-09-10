package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.client.input;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.input.KeyHelper1_16_5;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

public class KeyHelperFabric1_16_5 extends KeyHelper1_16_5<KeyMapping> {
    
    @Override public KeyAPI<KeyMapping> create(String id, String category, int keyCode) {
        return new KeyFabric1_16_5(new KeyMapping(id,keyCode,category));
    }
    
    @Override public void register(KeyAPI<KeyMapping> key) {
        KeyBindingHelper.registerKeyBinding(key.unwrap());
    }
}