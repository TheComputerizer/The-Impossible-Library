package mods.thecomputerizer.theimpossiblelibrary.legacy.client;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.KeyAPI;
import net.minecraft.client.settings.KeyBinding;

@Setter @Getter
public class KeyLegacy implements KeyAPI {

    private KeyBinding key;

    public KeyLegacy(KeyBinding key) {
        this.key = key;
    }

    @Override
    public boolean isDown() {
        return this.key.isKeyDown();
    }
}
