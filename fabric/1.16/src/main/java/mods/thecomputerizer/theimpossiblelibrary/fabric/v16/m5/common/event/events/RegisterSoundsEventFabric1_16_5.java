package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterSoundsEventFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.event.RegistryEventFabric1_16_5;
import net.fabricmc.fabric.api.event.Event;

public class RegisterSoundsEventFabric1_16_5 extends RegisterSoundsEventFabric implements RegistryEventFabric1_16_5 {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override public void register(SoundEventAPI<?> entry) {
        register("sound",entry);
    }
}
