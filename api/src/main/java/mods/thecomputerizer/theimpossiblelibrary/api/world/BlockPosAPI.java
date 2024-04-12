package mods.thecomputerizer.theimpossiblelibrary.api.world;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.util.VectorHelper;
import org.joml.Vector3i;

@Getter
public abstract class BlockPosAPI<P> {

    public static final BlockPosAPI<Vector3i> ZERO = new Zero(VectorHelper.ZERO_3I);

    protected P pos;
    protected Vector3i posVec;

    protected BlockPosAPI(P pos, Vector3i posVec) {
        this.pos = pos;
        this.posVec = posVec;
    }

    public BlockPosAPI<?> add(BlockPosAPI<?> api) {
        return add(api.x(),api.y(),api.z());
    }

    public BlockPosAPI<?> add(Vector3i posVec) {
        return add(posVec.x,posVec.y,posVec.z);
    }

    public abstract BlockPosAPI<?> add(P pos);
    public abstract BlockPosAPI<?> add(int x, int y, int z);

    public double distanceTo(BlockPosAPI<?> pos) {
        return distanceTo(pos.posVec);
    }

    public double distanceTo(Vector3i vec) {
        return this.posVec.distance(vec);
    }

    public BlockPosAPI<?> down() {
        return add(0,-1,0);
    }

    public BlockPosAPI<?> east() {
        return add(1,0,0);
    }

    public BlockPosAPI<?> north() {
        return add(0,0,-1);
    }

    public BlockPosAPI<?> south() {
        return add(0,0,1);
    }

    public BlockPosAPI<?> up() {
        return add(0,1,0);
    }

    public BlockPosAPI<?> west() {
        return add(-1,0,0);
    }

    public int x() {
        return this.posVec.x;
    }

    public int y() {
        return this.posVec.y;
    }

    public int z() {
        return this.posVec.z;
    }

    private static final class Zero extends BlockPosAPI<Vector3i> {

        private Zero(Vector3i pos) {
            super(pos,pos);
        }

        @Override
        public BlockPosAPI<?> add(Vector3i pos) {
            return pos.x==0 && pos.y==0 && pos.z==0 ? this : PosHelper.getPos(pos.add(this.posVec));
        }

        @Override
        public BlockPosAPI<?> add(int x, int y, int z) {
            return add(new Vector3i(x,y,z));
        }

        @Override
        public BlockPosAPI<?> down() {
            return add(0,-1,0);
        }

        @Override
        public BlockPosAPI<?> east() {
            return add(1,0,0);
        }

        @Override
        public BlockPosAPI<?> north() {
            return add(0,0,-1);
        }

        @Override
        public BlockPosAPI<?> south() {
            return add(0,0,1);
        }

        @Override
        public BlockPosAPI<?> up() {
            return add(0,1,0);
        }

        @Override
        public BlockPosAPI<?> west() {
            return add(-1,0,0);
        }
    }
}