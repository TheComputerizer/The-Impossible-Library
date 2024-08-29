package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerPickupXPEventWrapper;
import net.minecraftforge.event.entity.player.PlayerXpEvent.PickupXp;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_XP_PICKUP;

public class PlayerPickupXPEventForge extends PlayerPickupXPEventWrapper<PickupXp> {
    
    @SubscribeEvent
    public static void onEvent(PickupXp event) {
        PLAYER_XP_PICKUP.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(PickupXp event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<PickupXp,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(PickupXp::getPlayer);
    }

    @Override protected EventFieldWrapper<PickupXp,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(PickupXp::getOrb);
    }
}