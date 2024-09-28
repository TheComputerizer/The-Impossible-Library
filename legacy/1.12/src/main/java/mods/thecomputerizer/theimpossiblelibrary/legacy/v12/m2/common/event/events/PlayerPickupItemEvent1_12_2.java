package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerPickupItemEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ITEM_PICKUP;

public class PlayerPickupItemEvent1_12_2 extends PlayerPickupItemEventWrapper<ItemPickupEvent> {

    @SubscribeEvent
    public static void onEvent(ItemPickupEvent event) {
        PLAYER_ITEM_PICKUP.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(ItemPickupEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<ItemPickupEvent,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(ItemPickupEvent::getOriginalEntity);
    }

    @Override protected EventFieldWrapper<ItemPickupEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(event -> event.player);
    }

    @Override protected EventFieldWrapper<ItemPickupEvent,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(ItemPickupEvent::getStack);
    }
}