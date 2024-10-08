package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.input;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.input.KeyHelper1_16_5;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyHelperForge1_16_5 extends KeyHelper1_16_5 {
    
    @Override public void register(KeyAPI<?> key) {
        ClientRegistry.registerKeyBinding(key.unwrap());
    }
}