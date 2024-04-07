package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.TickableEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerTickEventWrapper;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.TickableEventWrapper.TickPhase.*;

public class PlayerTickEventLegacy extends PlayerTickEventWrapper<PlayerTickEvent> {

    @Override
    public TickableEventWrapper.TickPhase getPhase() {
        return Objects.nonNull(this.event) ? (this.event.phase==Phase.END ? END : START) : DEFAULT;
    }
}