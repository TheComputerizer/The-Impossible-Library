package mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ItemTooltipEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.ITEM_TOOLTIP;

public class ItemTooltipEventForge extends ItemTooltipEventWrapper<ItemTooltipEvent> {
    
    @SubscribeEvent
    public static void onEvent(ItemTooltipEvent event) {
        ITEM_TOOLTIP.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(ItemTooltipEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<ItemTooltipEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(ItemTooltipEvent::getPlayer);
    }

    @Override protected EventFieldWrapper<ItemTooltipEvent,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(ItemTooltipEvent::getItemStack);
    }

    @Override protected EventFieldWrapper<ItemTooltipEvent,List<String>> wrapTooltipField() { //TODO Adjust for text components
        return wrapGenericGetter(event -> new ArrayList<>(),new ArrayList<>());
    }
}