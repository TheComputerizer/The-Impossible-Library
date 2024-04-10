package mods.thecomputerizer.theimpossiblelibrary.legacy.block;

import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockSnapshotAPI;
import net.minecraftforge.common.util.BlockSnapshot;

public class BlockSnapShotLegacy implements BlockSnapshotAPI<BlockSnapshot> {

    private final BlockSnapshot snapshot;

    public BlockSnapShotLegacy(BlockSnapshot snapshot) {
        this.snapshot = snapshot;
    }

    @Override
    public BlockSnapshot getSnapshot() {
        return this.snapshot;
    }
}