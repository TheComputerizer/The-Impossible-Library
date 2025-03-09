package mods.thecomputerizer.theimpossiblelibrary.api.world;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

import static mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper.zero3I;

@Getter
public abstract class BlockPosAPI<P> extends AbstractWrapped<P> {

    @IndirectCallers
    public static final BlockPosAPI<Vector3> ZERO = new Zero(zero3I());

    protected Vector3 posVec;
    
    protected BlockPosAPI(P pos, int x, int y, int z) {
        this(pos,new Vector3(x,y,z));
    }

    protected BlockPosAPI(P pos, Vector3 posVec) {
        super(pos);
        this.posVec = posVec;
    }

    public BlockPosAPI<?> add(BlockPosAPI<?> api) {
        return add(api.x(),api.y(),api.z());
    }

    public BlockPosAPI<?> add(Vector3 posVec) {
        return add(posVec.iX(),posVec.iY(),posVec.iZ());
    }

    public abstract BlockPosAPI<?> add(P pos);
    public abstract BlockPosAPI<?> add(int x, int y, int z);

    public double distanceTo(BlockPosAPI<?> pos) {
        return distanceTo(pos.posVec);
    }

    public double distanceTo(Vector3 vec) {
        return this.posVec.distance(vec);
    }

    public BlockPosAPI<?> down() {
        return add(0,-1,0);
    }
    
    @IndirectCallers
    public BlockPosAPI<?> east() {
        return add(1,0,0);
    }
    
    @IndirectCallers
    public BlockPosAPI<?> north() {
        return add(0,0,-1);
    }
    
    @IndirectCallers
    public BlockPosAPI<?> south() {
        return add(0,0,1);
    }

    public BlockPosAPI<?> up() {
        return add(0,1,0);
    }
    
    @IndirectCallers
    public BlockPosAPI<?> west() {
        return add(-1,0,0);
    }

    public int x() {
        return this.posVec.iX();
    }

    public int y() {
        return this.posVec.iY();
    }

    public int z() {
        return this.posVec.iZ();
    }

    private static final class Zero extends BlockPosAPI<Vector3> {

        private Zero(Vector3 pos) {
            super(pos,pos);
        }

        @Override public BlockPosAPI<?> add(Vector3 pos) {
            return pos.iX()==0 && pos.iY()==0 && pos.iZ()==0 ? this : PosHelper.getPos(pos.add(this.posVec));
        }

        @Override public BlockPosAPI<?> add(int x, int y, int z) {
            return add(new Vector3(x,y,z));
        }

        @Override public BlockPosAPI<?> down() {
            return add(0,-1,0);
        }

        @Override public BlockPosAPI<?> east() {
            return add(1,0,0);
        }

        @Override public BlockPosAPI<?> north() {
            return add(0,0,-1);
        }

        @Override public BlockPosAPI<?> south() {
            return add(0,0,1);
        }

        @Override public BlockPosAPI<?> up() {
            return add(0,1,0);
        }

        @Override public BlockPosAPI<?> west() {
            return add(-1,0,0);
        }
    }
}