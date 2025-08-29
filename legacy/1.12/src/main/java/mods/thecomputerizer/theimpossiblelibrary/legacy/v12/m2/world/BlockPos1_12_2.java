package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world;

import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import net.minecraft.util.math.BlockPos;

public class BlockPos1_12_2 extends BlockPosAPI<BlockPos> {
    
    public static BlockPos1_12_2 get(Object obj) {
        return obj instanceof BlockPos ? pos(obj) : vec(obj);
    }
    
    public static BlockPos1_12_2 pos(Object pos) {
        return new BlockPos1_12_2((BlockPos)pos);
    }
    
    public static BlockPos1_12_2 vec(Object pos) {
        return new BlockPos1_12_2((Vector3)pos);
    }

    public BlockPos1_12_2(BlockPos pos) {
        this(pos,new Vector3(pos.getX(),pos.getY(),pos.getZ()));
    }

    public BlockPos1_12_2(Vector3 posVec) {
        this(new BlockPos(posVec.dX(),posVec.dY(),posVec.dZ()),posVec);
    }

    private BlockPos1_12_2(BlockPos pos, Vector3 posVec) {
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