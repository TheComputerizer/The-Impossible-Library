package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.KeyAPI;
import net.minecraft.client.settings.KeyBinding;

@Setter @Getter
public class KeyForge implements KeyAPI {

    private KeyBinding key;

    public KeyForge(KeyBinding key) {
        this.key = key;
    }

    @Override
    public boolean isDown() {
        return this.key.isDown();
    }
}
