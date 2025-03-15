package mods.thecomputerizer.theimpossiblelibrary.shared.v21.client.input;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import net.minecraft.client.KeyMapping;

public class Key1_21 extends KeyAPI<KeyMapping> {

    public Key1_21(Object key) {
        super((KeyMapping)key);
    }
    
    @Override public boolean isDown() {
        return this.wrapped.isDown();
    }
}
