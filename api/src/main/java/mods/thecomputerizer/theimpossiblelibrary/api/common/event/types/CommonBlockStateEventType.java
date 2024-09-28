package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;

public abstract class CommonBlockStateEventType<E> extends CommonEventWrapper<E> {

    protected EventFieldWrapper<E,BlockPosAPI<?>> pos;
    protected EventFieldWrapper<E,BlockStateAPI<?>> state;
    protected EventFieldWrapper<E,WorldAPI<?>> world;

    protected CommonBlockStateEventType(CommonType<?> type) {
        super(type);
    }

    public BlockPosAPI<?> getPos() {
        return this.pos.get(this.event);
    }

    public BlockStateAPI<?> getState() {
        return this.state.get(this.event);
    }

    public WorldAPI<?> getWorld() {
        return this.world.get(this.event);
    }

    @Override protected void populate() {
        this.pos = wrapPosField();
        this.state = wrapStateField();
        this.world = wrapWorldField();
    }

    protected abstract EventFieldWrapper<E,BlockPosAPI<?>> wrapPosField();
    protected abstract EventFieldWrapper<E,BlockStateAPI<?>> wrapStateField();
    protected abstract EventFieldWrapper<E,WorldAPI<?>> wrapWorldField();
}
