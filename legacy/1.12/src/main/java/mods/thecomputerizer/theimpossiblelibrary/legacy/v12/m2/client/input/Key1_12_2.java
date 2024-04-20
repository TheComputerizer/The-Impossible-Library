package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.input;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import net.minecraft.client.settings.KeyBinding;

public class Key1_12_2 extends KeyAPI<KeyBinding> {

    public Key1_12_2(KeyBinding key) {
        super(key);
    }

    @Override
    public boolean isDown() {
        return this.keybind.isKeyDown();
    }
}
