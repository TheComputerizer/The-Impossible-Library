package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ClientTickEventWrapper;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.TickableEventWrapper.TickPhase.*;

public class ClientTickEventForge extends ClientTickEventWrapper<ClientTickEvent> {

    private TickPhase phase = DEFAULT;

    @Override
    public void setEvent(ClientTickEvent event) {
        super.setEvent(event);
        this.phase = event.phase==Phase.END ? END : START;
    }

    @Override
    public TickPhase getPhase() {
        return this.phase;
    }
}