package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.input;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.input.KeyHelper1_16_5;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyHelperForge1_16_5 extends KeyHelper1_16_5<KeyBinding> {
    
    @Override public KeyAPI<KeyBinding> create(String id, String category, int keyCode) {
        return new KeyForge1_16_5(new KeyBinding(id,keyCode,category));
    }
    
    @Override public void register(KeyAPI<KeyBinding> key) {
        ClientRegistry.registerKeyBinding(key.unwrap());
    }
}