package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerSmeltedItemEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ITEM_SMELTED;

public class PlayerSmeltedItemEvent1_12_2 extends PlayerSmeltedItemEventWrapper<ItemSmeltedEvent> {

    @SubscribeEvent
    public static void onEvent(ItemSmeltedEvent event) {
        PLAYER_ITEM_SMELTED.invoke(event);
    }
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(ItemSmeltedEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override
    protected EventFieldWrapper<ItemSmeltedEvent,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(event -> event.smelting);
    }

    @Override
    protected EventFieldWrapper<ItemSmeltedEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(event -> event.player);
    }
}