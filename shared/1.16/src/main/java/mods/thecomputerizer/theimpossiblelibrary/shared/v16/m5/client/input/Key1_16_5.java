package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.input;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import net.minecraft.client.KeyMapping;

public class Key1_16_5 extends KeyAPI<KeyMapping> {

    public Key1_16_5(Object key) {
        super((KeyMapping)key);
    }
    
    @Override public boolean isDown() {
        return this.wrapped.isDown();
    }
}