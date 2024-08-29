package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.input;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import net.minecraft.client.settings.KeyBinding;

public class Key1_16_5 extends KeyAPI<KeyBinding> {

    public Key1_16_5(KeyBinding key) {
        super(key);
    }

    @Override
    public boolean isDown() {
        return this.keybind.isDown();
    }
}
