package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerAdvancementEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.AdvancementEvent;

public class PlayerAdvancementEvent1_16_5 extends PlayerAdvancementEventWrapper<AdvancementEvent> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(AdvancementEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<AdvancementEvent,AdvancementAPI<?>> wrapAdvancementField() {
        return wrapAdvancementGetter(AdvancementEvent::getAdvancement);
    }

    @Override
    protected EventFieldWrapper<AdvancementEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(AdvancementEvent::getPlayer);
    }
}