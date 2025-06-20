package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.client;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.client.NeoForgeHandlesClient1_20;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

public class NeoForgeHandlesClient1_20_4 extends NeoForgeHandlesClient1_20 {
    
    @Override public void registerKeyBindingsEvent(Object eventObj) {
        RegisterKeyMappingsEvent event = (RegisterKeyMappingsEvent)eventObj;
        TILRef.logInfo("Registering {} keybinds for 1.20",this.keys.size());
        for(KeyMapping key : this.keys) event.register(key);
        this.keys.clear();
        this.registeredKeys = true;
    }
}