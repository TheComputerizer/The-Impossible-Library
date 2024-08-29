package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world;

import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import net.minecraft.util.math.BlockPos;
import org.joml.Vector3i;

public class BlockPos1_16_5 extends BlockPosAPI<BlockPos> {

    public BlockPos1_16_5(BlockPos pos) {
        this(pos,new Vector3i(pos.getX(),pos.getY(),pos.getZ()));
    }

    public BlockPos1_16_5(Vector3i posVec) {
        this(new BlockPos(posVec.x,posVec.y,posVec.z),posVec);
    }

    public BlockPos1_16_5(double x, double y, double z) {
        this((int)x,(int)y,(int)z);
    }

    public BlockPos1_16_5(int x, int y, int z) {
        this(new BlockPos(x,y,z),new Vector3i(x,y,z));
    }

    private BlockPos1_16_5(BlockPos pos, Vector3i posVec) {
        super(pos,posVec);
    }

    @Override
    public BlockPosAPI<?> add(BlockPos pos) {
        return add(pos.getX(),pos.getY(),pos.getZ());
    }

    @Override
    public BlockPosAPI<?> add(int x, int y, int z) {
        BlockPos newPos = this.pos.offset(x,y,z);
        return newPos==this.pos ? this : new BlockPos1_16_5(newPos);
    }
}
