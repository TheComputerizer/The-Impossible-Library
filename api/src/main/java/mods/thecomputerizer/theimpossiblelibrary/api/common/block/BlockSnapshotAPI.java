package mods.thecomputerizer.theimpossiblelibrary.api.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.util.AbstractWrapped;

public abstract class BlockSnapshotAPI<S> extends AbstractWrapped<S> {

    protected BlockSnapshotAPI(S snapshot) {
        super(snapshot);
    }
}