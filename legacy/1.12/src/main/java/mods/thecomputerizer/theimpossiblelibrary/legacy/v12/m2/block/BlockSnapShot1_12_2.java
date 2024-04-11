package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.block;

import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockSnapshotAPI;
import net.minecraftforge.common.util.BlockSnapshot;

public class BlockSnapShot1_12_2 implements BlockSnapshotAPI<BlockSnapshot> {

    private final BlockSnapshot snapshot;

    public BlockSnapShot1_12_2(BlockSnapshot snapshot) {
        this.snapshot = snapshot;
    }

    @Override
    public BlockSnapshot getSnapshot() {
        return this.snapshot;
    }
}