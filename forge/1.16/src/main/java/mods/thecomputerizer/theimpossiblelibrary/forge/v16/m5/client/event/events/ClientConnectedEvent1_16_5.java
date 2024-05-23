package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ClientConnectedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent.LoggedInEvent;

public class ClientConnectedEvent1_16_5 extends ClientConnectedEventWrapper<LoggedInEvent> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LoggedInEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override
    protected EventFieldWrapper<LoggedInEvent,Boolean> wrapLocalField() {
        return wrapGenericGetter(event -> true,true);
    }

    @Override
    protected EventFieldWrapper<LoggedInEvent,String> wrapConnectionTypeField() {
        return wrapGenericGetter(event -> "","");
    }
}