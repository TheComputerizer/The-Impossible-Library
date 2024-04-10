package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ClientConnectedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent.LoggedInEvent;

public class ClientConnectedEventForge extends ClientConnectedEventWrapper<LoggedInEvent> { //TODO

    @Override
    protected EventFieldWrapper<LoggedInEvent,Boolean> wrapLocalField() {
        return wrapGenericGetter(event -> true,true);
    }

    @Override
    protected EventFieldWrapper<LoggedInEvent,String> wrapConnectionTypeField() {
        return wrapGenericGetter(event -> "","");
    }
}