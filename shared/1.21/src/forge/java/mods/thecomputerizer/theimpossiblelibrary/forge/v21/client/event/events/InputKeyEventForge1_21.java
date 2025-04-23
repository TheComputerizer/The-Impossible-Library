package mods.thecomputerizer.theimpossiblelibrary.forge.v21.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.InputKeyEventForge;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.InputEvent.Key;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.KEY_INPUT;

public class InputKeyEventForge1_21 extends InputKeyEventForge<Key> {
    
    @SubscribeEvent
    public static void onEvent(Key event) {
        KEY_INPUT.invoke(event);
    }
    
    @Override public boolean isKey(KeyAPI<?> key) {
        return ((KeyMapping)key.unwrap()).matches(this.event.getKey(), this.event.getScanCode());
    }
}