package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.input;

import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.input.Key1_16_5;
import net.minecraft.client.settings.KeyBinding;

public class KeyForge1_16_5 extends Key1_16_5<KeyBinding> {
    
    public KeyForge1_16_5(KeyBinding key) {
        super(key);
    }
    
    @Override public boolean isDown() {
        return this.keybind.isDown();
    }
}
