package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ClientTickEventWrapper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.TICK_CLIENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.TickableEventWrapper.TickPhase.*;

public class ClientTickEventLegacy extends ClientTickEventWrapper<ClientTickEvent> {

    @SubscribeEvent
    public static void onEvent(ClientTickEvent event) {
        TICK_CLIENT.invoke(event);
    }

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