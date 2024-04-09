package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockStateAPI;

public abstract class CommonPlayerStateEventType<E> extends CommonPlayerEventType<E> {

    protected EventFieldWrapper<E,BlockStateAPI<?>> state;

    protected CommonPlayerStateEventType(CommonType<?> type) {
        super(type);
    }

    public BlockStateAPI<?> getState() {
        return this.state.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.state = wrapStateField();
    }

    protected abstract EventFieldWrapper<E,BlockStateAPI<?>> wrapStateField();
}