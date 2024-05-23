package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.InputKeyEventWrapper;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;

public class InputKeyEvent1_16_5 extends InputKeyEventWrapper<KeyInputEvent> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(KeyInputEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}