package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.block.Facing;

import java.util.EnumSet;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_NOTIFY_NEIGHBOR;

public abstract class BlockNotifyNeighborEventWrapper<E> extends CommonEventWrapper<E> {

    protected EventFieldWrapper<E,Boolean> forceRedstoneUpdate;
    @Getter protected EnumSet<Facing> sides;

    protected BlockNotifyNeighborEventWrapper() {
        super(BLOCK_NOTIFY_NEIGHBOR);
    }

    public boolean forcesRedstoneUpdate() {
        return this.forceRedstoneUpdate.get(this.event);
    }

    @Override
    protected void populate() {
        this.forceRedstoneUpdate = wrapForceRedstoneUpdateField();
        this.sides = wrapSidesField();
    }

    protected abstract EventFieldWrapper<E,Boolean> wrapForceRedstoneUpdateField();
    protected abstract EnumSet<Facing> wrapSidesField();
}