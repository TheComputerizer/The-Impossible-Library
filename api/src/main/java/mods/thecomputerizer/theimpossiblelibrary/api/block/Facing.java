package mods.thecomputerizer.theimpossiblelibrary.api.block;

import lombok.Getter;

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

    @Getter
    public enum Axis {

        X(Plane.HORIZONTAL),
        Y(Plane.HORIZONTAL),
        Z(Plane.VERTICAL);

        private final Plane plane;

        Axis(Plane plane) {
            this.plane = plane;
        }

        public boolean isHorizontal() {
            return isPlane(Plane.HORIZONTAL);
        }

        public boolean isPlane(Plane plane) {
            return this.plane==plane;
        }

        public boolean isVertical() {
            return isPlane(Plane.VERTICAL);
        }
    }

    public enum Plane { HORIZONTAL, VERTICAL }
}