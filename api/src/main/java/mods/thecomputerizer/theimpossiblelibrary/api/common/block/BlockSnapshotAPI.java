package mods.thecomputerizer.theimpossiblelibrary.api.common.block;

import lombok.Getter;

@Getter
public abstract class BlockSnapshotAPI<S> {

    protected final S snapshot;

    protected BlockSnapshotAPI(S snapshot) {
        this.snapshot = snapshot;
    }
}