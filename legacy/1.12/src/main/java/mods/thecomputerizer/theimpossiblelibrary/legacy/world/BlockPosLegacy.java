package mods.thecomputerizer.theimpossiblelibrary.legacy.world;

import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import net.minecraft.util.math.BlockPos;
import org.joml.Vector3i;

public class BlockPosLegacy extends BlockPosAPI<BlockPos> {

    public BlockPosLegacy(BlockPos pos) {
        this(pos,new Vector3i(pos.getX(),pos.getY(),pos.getZ()));
    }

    public BlockPosLegacy(Vector3i posVec) {
        this(new BlockPos(posVec.x,posVec.y,posVec.z),posVec);
    }

    private BlockPosLegacy(BlockPos pos, Vector3i posVec) {
        super(pos,posVec);
    }

    @Override
    public BlockPosAPI<?> add(BlockPos pos) {
        return add(pos.getX(),pos.getY(),pos.getZ());
    }

    @Override
    public BlockPosAPI<?> add(int x, int y, int z) {
        BlockPos newPos = this.pos.add(x,y,z);
        return newPos==this.pos ? this : new BlockPosLegacy(newPos);
    }
}
