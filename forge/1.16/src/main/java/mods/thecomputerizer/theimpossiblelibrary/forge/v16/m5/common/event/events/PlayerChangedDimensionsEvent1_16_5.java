package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerChangedDimensionsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;

public class PlayerChangedDimensionsEvent1_16_5 extends PlayerChangedDimensionsEventWrapper<PlayerChangedDimensionEvent> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(PlayerChangedDimensionEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<PlayerChangedDimensionEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(PlayerChangedDimensionEvent::getPlayer);
    }
}