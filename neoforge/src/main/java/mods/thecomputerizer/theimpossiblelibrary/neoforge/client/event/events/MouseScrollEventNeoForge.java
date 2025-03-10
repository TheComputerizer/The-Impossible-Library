package mods.thecomputerizer.theimpossiblelibrary.neoforge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.MouseScrollEventWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent.MouseScrollingEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.MOUSE_SCROLL;

public class MouseScrollEventNeoForge extends MouseScrollEventWrapper<MouseScrollingEvent> {
    
    @SubscribeEvent
    public static void onEvent(MouseScrollingEvent event) {
        MOUSE_SCROLL.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(MouseScrollingEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}