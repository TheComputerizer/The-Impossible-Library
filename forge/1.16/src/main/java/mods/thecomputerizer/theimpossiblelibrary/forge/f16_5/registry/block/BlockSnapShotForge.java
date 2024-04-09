package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockSnapshotAPI;
import net.minecraftforge.common.util.BlockSnapshot;

public class BlockSnapShotForge implements BlockSnapshotAPI<BlockSnapshot> {

    private final BlockSnapshot snapshot;

    public BlockSnapShotForge(BlockSnapshot snapshot) {
        this.snapshot = snapshot;
    }

    @Override
    public BlockSnapshot getSnapshot() {
        return this.snapshot;
    }
}