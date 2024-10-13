package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.world;

import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import net.minecraft.core.BlockPos;
import org.joml.Vector3i;

public class BlockPos1_18_2 extends BlockPosAPI<BlockPos> {
    
    public static BlockPos1_18_2 get(Object obj) {
        return obj instanceof BlockPos ? pos(obj) : vec(obj);
    }
    
    public static BlockPos1_18_2 pos(Object pos) {
        return new BlockPos1_18_2((BlockPos)pos);
    }
    
    public static BlockPos1_18_2 vec(Object pos) {
        return new BlockPos1_18_2((Vector3i)pos);
    }

    public BlockPos1_18_2(BlockPos pos) {
        this(pos,new Vector3i(pos.getX(),pos.getY(),pos.getZ()));
    }

    public BlockPos1_18_2(Vector3i posVec) {
        this(new BlockPos(posVec.x,posVec.y,posVec.z),posVec);
    }

    public BlockPos1_18_2(double x, double y, double z) {
        this((int)x,(int)y,(int)z);
    }

    public BlockPos1_18_2(int x, int y, int z) {
        this(new BlockPos(x,y,z),new Vector3i(x,y,z));
    }

    private BlockPos1_18_2(BlockPos pos, Vector3i posVec) {
        super(pos,posVec);
    }

    @Override public BlockPosAPI<?> add(BlockPos pos) {
        return add(pos.getX(),pos.getY(),pos.getZ());
    }

    @Override public BlockPosAPI<?> add(int x, int y, int z) {
        BlockPos newPos = this.wrapped.offset(x,y,z);
        return newPos==this.wrapped ? this : new BlockPos1_18_2(newPos);
    }
}