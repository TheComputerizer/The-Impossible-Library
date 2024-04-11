package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.KeyAPI;
import net.minecraft.client.settings.KeyBinding;

@Setter @Getter
public class Key1_12_2 implements KeyAPI {

    private KeyBinding key;

    public Key1_12_2(KeyBinding key) {
        this.key = key;
    }

    @Override
    public boolean isDown() {
        return this.key.isKeyDown();
    }
}
