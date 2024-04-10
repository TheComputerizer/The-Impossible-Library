package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ItemTooltipEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import java.util.ArrayList;
import java.util.List;

public class ItemTooltipEventForge extends ItemTooltipEventWrapper<ItemTooltipEvent> {

    @Override
    protected EventFieldWrapper<ItemTooltipEvent,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(ItemTooltipEvent::getPlayer);
    }

    @Override
    protected EventFieldWrapper<ItemTooltipEvent,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(ItemTooltipEvent::getItemStack);
    }

    @Override
    protected EventFieldWrapper<ItemTooltipEvent,List<String>> wrapTooltipField() { //TODO Adjust for text components
        return wrapGenericGetter(event -> new ArrayList<>(),new ArrayList<>());
    }
}