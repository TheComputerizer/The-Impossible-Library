package mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.InputKeyEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.KEY_INPUT;

public class InputKeyEventForge extends InputKeyEventWrapper<KeyInputEvent> {
    
    @SubscribeEvent
    public static void onEvent(KeyInputEvent event) {
        KEY_INPUT.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(KeyInputEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override public boolean isKey(KeyAPI<?> key) {
        return ((KeyBinding)key.unwrap()).matches(this.event.getKey(),this.event.getScanCode());
    }
}