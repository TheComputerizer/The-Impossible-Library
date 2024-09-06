package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.client.input;

import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.input.Key1_16_5;
import net.minecraft.client.KeyMapping;

public class KeyFabric1_16_5 extends Key1_16_5<KeyMapping> {
    
    public KeyFabric1_16_5(KeyMapping key) {
        super(key);
    }
    
    @Override public boolean isDown() {
        return this.keybind.isDown();
    }
}
