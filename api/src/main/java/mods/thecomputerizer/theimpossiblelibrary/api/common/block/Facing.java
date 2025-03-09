package mods.thecomputerizer.theimpossiblelibrary.api.common.block;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Plane;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.ShapeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;

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

        X(new Vector3(1d, 0d, 0d), true),
        Y(new Vector3(0d,1d,0d),false),
        Z(new Vector3(0d,0d,1d),true);
        
        private final Vector3 direction;
        @Getter private final boolean horizontal;

        Axis(Vector3 direction, boolean horizontal) {
            this.direction = direction;
            this.horizontal = horizontal;
        }
        
        /**
         Return a new vector instance to avoid modifying data
         */
        public Vector3 getDirection() {
            return new Vector3(this.direction);
        }
        
        public Plane getPlane() {
            return ShapeHelper.plane(this);
        }

        public boolean isVertical() {
            return !this.horizontal;
        }
    }
}