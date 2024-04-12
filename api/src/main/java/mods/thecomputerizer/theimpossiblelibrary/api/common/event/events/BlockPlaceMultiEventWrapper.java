package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonBlockStateEntityEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockSnapshotAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;

import java.util.List;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_PLACE_MULTI;

public abstract class BlockPlaceMultiEventWrapper<E> extends CommonBlockStateEntityEventType<E> {

    protected EventFieldWrapper<E,BlockStateAPI<?>> placed;
    protected EventFieldWrapper<E,BlockStateAPI<?>> placedAgainst;
    protected EventFieldWrapper<E,WorldAPI<?>> world;
    protected EventFieldWrapper<E,BlockSnapshotAPI<?>> snapshot;
    protected EventFieldWrapper<E,List<BlockSnapshotAPI<?>>> snapshots;

    protected BlockPlaceMultiEventWrapper() {
        super(BLOCK_PLACE_MULTI);
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

    public List<BlockSnapshotAPI<?>> getSnapshots() {
        return this.snapshots.get(this.event);
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
        this.snapshots = wrapSnapshotsField();
        this.world = wrapWorldField();
    }

    protected abstract EventFieldWrapper<E,BlockStateAPI<?>> wrapPlacedAgainstField();
    protected abstract EventFieldWrapper<E,BlockStateAPI<?>> wrapPlacedField();
    protected abstract EventFieldWrapper<E,BlockSnapshotAPI<?>> wrapSnapshotField();
    protected abstract EventFieldWrapper<E,List<BlockSnapshotAPI<?>>> wrapSnapshotsField();
    protected abstract EventFieldWrapper<E,WorldAPI<?>> wrapWorldField();
}