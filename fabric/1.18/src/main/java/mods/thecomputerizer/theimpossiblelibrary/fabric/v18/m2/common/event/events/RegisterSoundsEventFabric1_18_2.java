package mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterSoundsEventFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.common.event.RegistryEventFabric1_18_2;
import net.fabricmc.fabric.api.event.Event;

public class RegisterSoundsEventFabric1_18_2 extends RegisterSoundsEventFabric implements RegistryEventFabric1_18_2 {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override public void register(SoundEventAPI<?> entry) {
        register("sound",entry);
    }
}