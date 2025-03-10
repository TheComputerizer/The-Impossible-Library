package mods.thecomputerizer.theimpossiblelibrary.neoforge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.InputKeyEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import net.minecraft.client.KeyMapping;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent.Key;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.KEY_INPUT;

public class InputKeyEventNeoForge extends InputKeyEventWrapper<Key> {
    
    @SubscribeEvent
    public static void onEvent(Key event) {
        KEY_INPUT.invoke(event);
    }
    
    @Override public boolean isKey(KeyAPI<?> key) {
        return ((KeyMapping)key.unwrap()).matches(this.event.getKey(), this.event.getScanCode());
    }
    
    @Override public void setEvent(Key event) {
        super.setEvent(event);
    }
}