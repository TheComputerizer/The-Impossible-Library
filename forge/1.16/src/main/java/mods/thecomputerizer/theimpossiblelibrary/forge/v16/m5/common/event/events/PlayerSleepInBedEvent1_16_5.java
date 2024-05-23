package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerSleepInBedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;

public class PlayerSleepInBedEvent1_16_5 extends PlayerSleepInBedEventWrapper<PlayerSleepInBedEvent> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(PlayerSleepInBedEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<PlayerSleepInBedEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(PlayerSleepInBedEvent::getPlayer);
    }

    @Override
    protected EventFieldWrapper<PlayerSleepInBedEvent,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(PlayerSleepInBedEvent::getPos);
    }
}