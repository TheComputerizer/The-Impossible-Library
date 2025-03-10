package mods.thecomputerizer.theimpossiblelibrary.neoforge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ItemTooltipEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.ArrayList;
import java.util.List;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.ITEM_TOOLTIP;

public class ItemTooltipEventNeoForge extends ItemTooltipEventWrapper<ItemTooltipEvent> {
    
    @SubscribeEvent
    public static void onEvent(ItemTooltipEvent event) {
        ITEM_TOOLTIP.invoke(event);
    }
    
    @Override public void setEvent(ItemTooltipEvent event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<ItemTooltipEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(ItemTooltipEvent::getEntity);
    }

    @Override protected EventFieldWrapper<ItemTooltipEvent,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(ItemTooltipEvent::getItemStack);
    }

    @Override protected EventFieldWrapper<ItemTooltipEvent,List<String>> wrapTooltipField() { //TODO Adjust for text components
        return wrapGenericGetter(event -> new ArrayList<>(),new ArrayList<>());
    }
}