package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.KeyAPI;
import net.minecraft.client.settings.KeyBinding;

@Setter @Getter
public class Key1_16_5 implements KeyAPI {

    private KeyBinding key;

    public Key1_16_5(KeyBinding key) {
        this.key = key;
    }

    @Override
    public boolean isDown() {
        return this.key.isDown();
    }
}
