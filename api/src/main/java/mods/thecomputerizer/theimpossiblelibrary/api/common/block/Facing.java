package mods.thecomputerizer.theimpossiblelibrary.api.common.block;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Plane;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.ShapeHelper;
import org.joml.Vector3d;

@Getter
public enum Facing {

    DOWN(Axis.Y,false),
    EAST(Axis.X,true),
    NORTH(Axis.Z,false),
    SOUTH(Axis.Z,true),
    UP(Axis.Y,true),
    WEST(Axis.X,false);

    private final Axis axis;
    private final boolean positive;

    Facing(Axis axis, boolean positive) {
        this.axis = axis;
        this.positive = positive;
    }
    
    @SuppressWarnings("unused")
    public enum Axis {

        X(new Vector3d(1d,0d,0d),true),
        Y(new Vector3d(0d,1d,0d),false),
        Z(new Vector3d(0d,0d,1d),true);
        
        private final Vector3d direction;
        @Getter private final boolean horizontal;

        Axis(Vector3d direction, boolean horizontal) {
            this.direction = direction;
            this.horizontal = horizontal;
        }
        
        /**
         Return a new vector instance to avoid modify data
         */
        public Vector3d getDirection() {
            return new Vector3d(this.direction);
        }
        
        public Plane getPlane() {
            return ShapeHelper.plane(this);
        }

        public boolean isVertical() {
            return !this.horizontal;
        }
    }
}