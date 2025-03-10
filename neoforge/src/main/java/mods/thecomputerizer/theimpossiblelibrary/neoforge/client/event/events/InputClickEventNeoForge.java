package mods.thecomputerizer.theimpossiblelibrary.neoforge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.InputClickEventWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent.MouseButton;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CLICK_INPUT;

public class InputClickEventNeoForge extends InputClickEventWrapper<MouseButton> {
    
    @SubscribeEvent
    public static void onEvent(MouseButton event) {
        CLICK_INPUT.invoke(event);
    }
    
    @Override public void setEvent(MouseButton event) {
        super.setEvent(event);
    }

}