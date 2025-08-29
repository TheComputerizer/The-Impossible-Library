package mods.thecomputerizer.theimpossiblelibrary.shared.v20.client.input;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import net.minecraft.client.KeyMapping;

public class Key1_20 extends KeyAPI<KeyMapping> {

    public Key1_20(Object key) {
        super(key);
    }
    
    @Override public boolean isDown() {
        return getIfNotNullOrDefault(KeyMapping::isDown,false);
    }
}
