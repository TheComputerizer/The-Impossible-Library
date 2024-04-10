package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.block.PortalSize;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonBlockStateEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_SPAWN_PORTAL;

public abstract class SpawnPortalEventWrapper<E> extends CommonBlockStateEventType<E> {

    protected EventFieldWrapper<E,PortalSize> size;

    protected SpawnPortalEventWrapper() {
        super(BLOCK_SPAWN_PORTAL);
    }

    public PortalSize getSize() {
        return this.size.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.size = wrapSizeField();
    }

    protected abstract EventFieldWrapper<E,PortalSize> wrapSizeField();
}