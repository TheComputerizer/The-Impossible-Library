package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ItemTooltipEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.ITEM_TOOLTIP;

public class ItemTooltipEvent1_12_2 extends ItemTooltipEventWrapper<ItemTooltipEvent> {

    @SubscribeEvent
    public static void onEvent(ItemTooltipEvent event) {
        ITEM_TOOLTIP.invoke(event);
    }

    @Override
    protected EventFieldWrapper<ItemTooltipEvent,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(ItemTooltipEvent::getEntityPlayer);
    }

    @Override
    protected EventFieldWrapper<ItemTooltipEvent,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(ItemTooltipEvent::getItemStack);
    }

    @Override
    protected EventFieldWrapper<ItemTooltipEvent,List<String>> wrapTooltipField() {
        return wrapGenericGetter(ItemTooltipEvent::getToolTip,new ArrayList<>());
    }
}