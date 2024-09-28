package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientPlayerEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;

import javax.annotation.Nullable;
import java.util.List;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.ITEM_TOOLTIP;

public abstract class ItemTooltipEventWrapper<E> extends ClientPlayerEventType<E> {

    protected EventFieldWrapper<E,ItemStackAPI<?>> stack;

    protected EventFieldWrapper<E,List<String>> tooltip;

    protected ItemTooltipEventWrapper() {
        super(ITEM_TOOLTIP);
    }

    public @Nullable ItemStackAPI<?> getStack() {
        return this.stack.get(this.event);
    }

    public List<String> getToolTip() {
        return this.tooltip.get(this.event);
    }

    @Override public void populate() {
        super.populate();
        this.stack = wrapStackField();
        this.tooltip = wrapTooltipField();
    }

    protected abstract EventFieldWrapper<E,ItemStackAPI<?>> wrapStackField();
    protected abstract EventFieldWrapper<E,List<String>> wrapTooltipField();
}