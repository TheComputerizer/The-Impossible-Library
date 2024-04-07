package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.WorldTickEventWrapper;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.TickableEventWrapper.TickPhase.*;

public class WorldTickEventLegacy extends WorldTickEventWrapper<WorldTickEvent> {

    @Override
    public TickPhase getPhase() {
        return Objects.nonNull(this.event) ? (this.event.phase==Phase.END ? END : START) : DEFAULT;
    }
}