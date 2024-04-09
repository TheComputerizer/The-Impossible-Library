package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonBlockStateEntityEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockSnapshotAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockStateAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_PLACE;

public abstract class BlockPlaceEventWrapper<E> extends CommonBlockStateEntityEventType<E> {

    protected EventFieldWrapper<E,BlockStateAPI<?>> placed;
    protected EventFieldWrapper<E,BlockStateAPI<?>> placedAgainst;
    protected EventFieldWrapper<E,BlockSnapshotAPI<?>> snapshot;
    protected EventFieldWrapper<E,WorldAPI<?>> world;

    protected BlockPlaceEventWrapper() {
        super(BLOCK_PLACE);
    }

    public BlockStateAPI<?> getPlaced() {
        return this.placed.get(this.event);
    }

    public BlockStateAPI<?> getPlacedAgainst() {
        return this.placedAgainst.get(this.event);
    }

    public BlockSnapshotAPI<?> getSnapshot() {
        return this.snapshot.get(this.event);
    }

    public WorldAPI<?> getWorld() {
        return this.world.get(this.event);
    }

    @Override
    protected void populate() {
        super.populate();
        this.placed = wrapPlacedField();
        this.placedAgainst = wrapPlacedAgainstField();
        this.snapshot = wrapSnapshotField();
        this.world = wrapWorldField();
    }

    protected abstract EventFieldWrapper<E,BlockStateAPI<?>> wrapPlacedAgainstField();
    protected abstract EventFieldWrapper<E,BlockStateAPI<?>> wrapPlacedField();
    protected abstract EventFieldWrapper<E,BlockSnapshotAPI<?>> wrapSnapshotField();
    protected abstract EventFieldWrapper<E,WorldAPI<?>> wrapWorldField();
}