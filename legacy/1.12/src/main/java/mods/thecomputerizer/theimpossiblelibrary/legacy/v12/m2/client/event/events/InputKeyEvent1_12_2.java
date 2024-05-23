package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.InputKeyEventWrapper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.KEY_INPUT;

public class InputKeyEvent1_12_2 extends InputKeyEventWrapper<KeyInputEvent> {

    @SubscribeEvent
    public static void onEvent(KeyInputEvent event) {
        KEY_INPUT.invoke(event);
    }
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(KeyInputEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}