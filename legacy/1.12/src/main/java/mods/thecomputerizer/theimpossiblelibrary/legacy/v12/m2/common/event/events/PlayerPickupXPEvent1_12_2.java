package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerPickupXPEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_XP_PICKUP;

public class PlayerPickupXPEvent1_12_2 extends PlayerPickupXPEventWrapper<PlayerPickupXpEvent> {

    @SubscribeEvent
    public static void onEvent(PlayerPickupXpEvent event) {
        PLAYER_XP_PICKUP.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(PlayerPickupXpEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<PlayerPickupXpEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(PlayerPickupXpEvent::getEntityPlayer);
    }

    @Override protected EventFieldWrapper<PlayerPickupXpEvent,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(PlayerPickupXpEvent::getOrb);
    }
}