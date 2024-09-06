package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world;

import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import net.minecraft.util.math.BlockPos;
import org.joml.Vector3i;

public class BlockPos1_12_2 extends BlockPosAPI<BlockPos> {

    public BlockPos1_12_2(BlockPos pos) {
        this(pos,new Vector3i(pos.getX(),pos.getY(),pos.getZ()));
    }

    public BlockPos1_12_2(Vector3i posVec) {
        this(new BlockPos(posVec.x,posVec.y,posVec.z),posVec);
    }

    public BlockPos1_12_2(double x, double y, double z) {
        this((int)x,(int)y,(int)z);
    }

    public BlockPos1_12_2(int x, int y, int z) {
        this(new BlockPos(x,y,z),new Vector3i(x,y,z));
    }

    private BlockPos1_12_2(BlockPos pos, Vector3i posVec) {
        super(pos,posVec);
    }

    @Override public BlockPosAPI<?> add(BlockPos pos) {
        return add(pos.getX(),pos.getY(),pos.getZ());
    }

    @Override public BlockPosAPI<?> add(int x, int y, int z) {
        BlockPos newPos = this.wrapped.add(x,y,z);
        return newPos==this.wrapped ? this : new BlockPos1_12_2(newPos);
    }
}