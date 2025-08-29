package mods.thecomputerizer.theimpossiblelibrary.shared.v19.world;

import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;

public class BlockPos1_19 extends BlockPosAPI<BlockPos> {
    
    public static BlockPos1_19 get(Object obj) {
        return obj instanceof BlockPos ? pos(obj) : vec(obj);
    }
    
    public static BlockPos1_19 pos(Object pos) {
        return new BlockPos1_19((BlockPos)pos);
    }
    
    public static BlockPos1_19 vec(Object pos) {
        return new BlockPos1_19((Vector3)pos);
    }

    public BlockPos1_19(BlockPos pos) {
        this(pos,new Vector3(pos.getX(),pos.getY(),pos.getZ()));
    }

    public BlockPos1_19(Vector3 posVec) {
        this(new BlockPos(Mth.floor(posVec.dX()),Mth.floor(posVec.dY()),Mth.floor(posVec.dZ())),posVec);
    }

    private BlockPos1_19(BlockPos pos, Vector3 posVec) {
        super(pos,posVec);
    }

    @Override public BlockPosAPI<?> add(BlockPos pos) {
        return add(pos.getX(),pos.getY(),pos.getZ());
    }

    @Override public BlockPosAPI<?> add(int x, int y, int z) {
        BlockPos newPos = this.wrapped.offset(x,y,z);
        return newPos==this.wrapped ? this : new BlockPos1_19(newPos);
    }
}