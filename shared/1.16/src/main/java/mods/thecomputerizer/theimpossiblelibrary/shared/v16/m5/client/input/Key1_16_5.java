package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.input;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import net.minecraft.client.settings.KeyBinding;

public class Key1_16_5 extends KeyAPI<KeyBinding> {

    public Key1_16_5(Object key) {
        super((KeyBinding)key);
    }
    
    @Override public boolean isDown() {
        return this.wrapped.isDown();
    }
}
