package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.WorldCreateSpawnPosEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.WorldEvent.CreateSpawnPosition;

public class WorldCreateSpawnPosEvent1_16_5 extends WorldCreateSpawnPosEventWrapper<CreateSpawnPosition> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(CreateSpawnPosition event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<CreateSpawnPosition,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(CreateSpawnPosition::getWorld);
    }
}