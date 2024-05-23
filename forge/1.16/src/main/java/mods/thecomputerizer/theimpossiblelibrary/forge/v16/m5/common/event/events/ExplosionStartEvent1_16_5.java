package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.ExplosionStartEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.ExplosionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.ExplosionEvent.Start;

public class ExplosionStartEvent1_16_5 extends ExplosionStartEventWrapper<Start> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Start event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<Start,ExplosionAPI<?>> wrapExplosionField() {
        return wrapExplosionGetter(Start::getExplosion);
    }

    @Override
    protected EventFieldWrapper<Start,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(Start::getWorld);
    }
}