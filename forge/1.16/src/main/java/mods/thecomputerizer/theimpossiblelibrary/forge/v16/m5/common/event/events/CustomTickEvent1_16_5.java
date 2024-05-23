package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.CustomTickEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.util.CustomTick1_16_5;

import java.util.Objects;

public class CustomTickEvent1_16_5 extends CustomTickEventWrapper<CustomTick1_16_5> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(CustomTick1_16_5 event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected CustomTick wrapTicker() {
        return Objects.nonNull(this.event) ? this.event.getTicker() : null;
    }
}