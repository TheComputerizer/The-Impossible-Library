package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.input;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import net.minecraft.client.KeyMapping;

public class Key1_16_5 extends KeyAPI<KeyMapping> {

    public Key1_16_5(Object key) {
        super(key);
    }
    
    @Override public boolean isDown() {
        return getIfNotNullOrDefault(KeyMapping::isDown,false);
    }
}