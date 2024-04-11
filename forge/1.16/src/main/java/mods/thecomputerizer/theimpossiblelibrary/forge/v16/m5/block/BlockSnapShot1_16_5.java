package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.block;

import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockSnapshotAPI;
import net.minecraftforge.common.util.BlockSnapshot;

public class BlockSnapShot1_16_5 implements BlockSnapshotAPI<BlockSnapshot> {

    private final BlockSnapshot snapshot;

    public BlockSnapShot1_16_5(BlockSnapshot snapshot) {
        this.snapshot = snapshot;
    }

    @Override
    public BlockSnapshot getSnapshot() {
        return this.snapshot;
    }
}