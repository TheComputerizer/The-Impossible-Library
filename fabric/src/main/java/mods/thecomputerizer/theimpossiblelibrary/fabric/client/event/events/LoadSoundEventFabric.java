package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.LoadSoundEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.ClientFabricEvent;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.resources.sounds.SoundInstance;

public class LoadSoundEventFabric extends LoadSoundEventWrapper<Object[],SoundInstance> implements ClientFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }

}