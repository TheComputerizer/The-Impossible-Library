package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.server.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.events.ServerTickEventWrapper;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.*;

public class ServerTickEventForge extends ServerTickEventWrapper<ServerTickEvent> {

    @Override
    protected TickPhase wrapTickPhase() {
        return Objects.nonNull(this.event) ? (event.phase==Phase.END ? END : START) : DEFAULT;
    }
}