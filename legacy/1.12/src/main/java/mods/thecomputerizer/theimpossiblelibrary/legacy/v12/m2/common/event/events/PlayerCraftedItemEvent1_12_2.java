package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerCraftedItemEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ITEM_CRAFTED;

public class PlayerCraftedItemEvent1_12_2 extends PlayerCraftedItemEventWrapper<ItemCraftedEvent> {

    @SubscribeEvent
    public static void onEvent(ItemCraftedEvent event) {
        PLAYER_ITEM_CRAFTED.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(ItemCraftedEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<ItemCraftedEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(event -> event.player);
    }

    @Override protected EventFieldWrapper<ItemCraftedEvent,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(event -> event.crafting);
    }
}