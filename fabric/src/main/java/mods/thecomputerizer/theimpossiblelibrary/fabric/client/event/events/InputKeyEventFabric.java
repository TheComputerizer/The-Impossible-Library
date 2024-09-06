package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.InputKeyEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.ClientFabricEvent;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.KeyMapping;

import static mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CustomFabricEvents.KEY_PRESSED;

public class InputKeyEventFabric extends InputKeyEventWrapper<Object[]> implements ClientFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return KEY_PRESSED;
    }
    
    @Override public boolean isKey(KeyAPI<?> key) {
        return ((KeyMapping)key.getKeybind()).matches((Integer)this.event[0],(Integer)this.event[1]);
    }
}