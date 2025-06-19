package mods.thecomputerizer.theimpossiblelibrary.api.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

public abstract class BlockSnapshotAPI<S> extends AbstractWrapped<S> {

    @SuppressWarnings("unchecked")
    protected BlockSnapshotAPI(Object snapshot) {
        super((S)snapshot);
    }
}