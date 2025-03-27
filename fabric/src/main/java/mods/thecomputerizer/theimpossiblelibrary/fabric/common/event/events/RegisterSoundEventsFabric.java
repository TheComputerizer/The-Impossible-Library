package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterSoundsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.RegistryEventFabric;
import net.fabricmc.fabric.api.event.Event;

import static mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CustomComonFabricEvents.REGISTER_SOUND_EVENTS;

public abstract class RegisterSoundEventsFabric extends RegisterSoundsEventWrapper<Object[]> implements RegistryEventFabric {
    
    @Override public Event<?> getEventInstance() {
        return REGISTER_SOUND_EVENTS;
    }
    
    @Override public void register(SoundEventAPI<?> entry) {
        registerEntry(entry);
    }
}
