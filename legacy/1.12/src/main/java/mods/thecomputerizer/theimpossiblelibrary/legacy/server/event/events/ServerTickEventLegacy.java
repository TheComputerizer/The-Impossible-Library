package mods.thecomputerizer.theimpossiblelibrary.legacy.server.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.server.event.events.ServerTickEventWrapper;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.TickableEventWrapper.TickPhase.*;

public class ServerTickEventLegacy extends ServerTickEventWrapper<ServerTickEvent> {

    @Override
    public TickPhase getPhase() {
        return Objects.nonNull(this.event) ? (this.event.phase==Phase.END ? END : START) : DEFAULT;
    }
}