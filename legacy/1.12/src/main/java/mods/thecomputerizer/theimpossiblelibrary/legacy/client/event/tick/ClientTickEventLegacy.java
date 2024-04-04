package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.tick;

import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.tick.ClientTickEventWrapper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.TICK_CLIENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.tick.TickableEventWrapper.TickPhase.*;

@Setter
public class ClientTickEventLegacy extends ClientTickEventWrapper<ClientTickEvent> {

    @SubscribeEvent
    public static void onEvent(ClientTickEvent event) {
        TICK_CLIENT.invoke(event);
    }

    private ClientTickEvent event;

    public ClientTickEventLegacy() {}

    @Override
    public TickPhase getPhase() {
        return Objects.nonNull(this.event) ? (this.event.phase==Phase.END ? END : START) : DEFAULT;
    }
}