package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerPickupXPEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerXpEvent.PickupXp;

public class PlayerPickupXPEvent1_16_5 extends PlayerPickupXPEventWrapper<PickupXp> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(PickupXp event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<PickupXp,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(PickupXp::getPlayer);
    }

    @Override
    protected EventFieldWrapper<PickupXp,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(PickupXp::getOrb);
    }
}