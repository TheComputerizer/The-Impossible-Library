package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.client.input;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import net.minecraft.client.KeyMapping;

public class Key1_18_2 extends KeyAPI<KeyMapping> {

    public Key1_18_2(Object key) {
        super((KeyMapping)key);
    }
    
    @Override public boolean isDown() {
        return this.wrapped.isDown();
    }
}
