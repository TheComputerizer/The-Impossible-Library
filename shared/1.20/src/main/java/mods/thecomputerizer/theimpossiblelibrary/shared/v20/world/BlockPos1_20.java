package mods.thecomputerizer.theimpossiblelibrary.shared.v20.world;

import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import net.minecraft.core.BlockPos;

public class BlockPos1_20 extends BlockPosAPI<BlockPos> {
    
    public static BlockPos1_20 get(Object obj) {
        return obj instanceof BlockPos ? pos(obj) : vec(obj);
    }
    
    public static BlockPos1_20 pos(Object pos) {
        return new BlockPos1_20((BlockPos)pos);
    }
    
    public static BlockPos1_20 vec(Object pos) {
        return new BlockPos1_20((Vector3)pos);
    }
    
    public BlockPos1_20(BlockPos pos) {
        this(pos,new Vector3(pos.getX(),pos.getY(),pos.getZ()));
    }
    
    public BlockPos1_20(Vector3 posVec) {
        this(new BlockPos(posVec.iX(),posVec.iY(),posVec.iZ()),posVec);
    }
    
    public BlockPos1_20(double x, double y, double z) {
        this((int)x,(int)y,(int)z);
    }
    
    public BlockPos1_20(int x, int y, int z) {
        this(new BlockPos(x,y,z),new Vector3(x,y,z));
    }
    
    private BlockPos1_20(BlockPos pos, Vector3 posVec) {
        super(pos,posVec);
    }

    @Override public BlockPosAPI<?> add(BlockPos pos) {
        return add(pos.getX(),pos.getY(),pos.getZ());
    }

    @Override public BlockPosAPI<?> add(int x, int y, int z) {
        BlockPos newPos = this.wrapped.offset(x,y,z);
        return newPos==this.wrapped ? this : new BlockPos1_20(newPos);
    }
}