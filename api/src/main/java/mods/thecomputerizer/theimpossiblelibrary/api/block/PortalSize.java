package mods.thecomputerizer.theimpossiblelibrary.api.block;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.block.Facing.Axis;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

public class PortalSize { //TODO Finish this

    private final WorldAPI<?> world;
    private final Axis axis;
    private final Facing rightDir;
    private int blockCount;
    private BlockPosAPI<?> bottomLeft;
    @Getter private int height;
    @Getter private int width;

    public PortalSize(WorldAPI<?> world, Axis axis, Facing rightDir) {
        this.world = world;
        this.axis = axis;
        this.rightDir = rightDir;
    }
}