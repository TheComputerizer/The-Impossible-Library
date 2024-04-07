package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ItemTooltipEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemStackAPI;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import java.util.ArrayList;
import java.util.List;

public class ItemTooltipEventLegacy extends ItemTooltipEventWrapper<ItemTooltipEvent> {

    @Override
    protected EventFieldWrapper<ItemTooltipEvent,PlayerAPI<?>> wrapPlayerField() {
        return getFieldWrapperGetter(event -> wrapPlayer(ItemTooltipEvent::getEntityPlayer),null);
    }

    @Override
    protected EventFieldWrapper<ItemTooltipEvent,ItemStackAPI<?>> wrapStackField() {
        return getFieldWrapperGetter(event -> wrapItemStack(ItemTooltipEvent::getItemStack),null);
    }

    @Override
    protected EventFieldWrapper<ItemTooltipEvent,List<String>> wrapTooltipField() {
        return getFieldWrapperGetter(ItemTooltipEvent::getToolTip,new ArrayList<>());
    }
}