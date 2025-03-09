package mods.thecomputerizer.theimpossiblelibrary.api.world;

import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;

public class PosHelper {

    public static BlockPosAPI<?> getPos(Object vec) {
        return WrapperHelper.wrapPosition(vec);
    }

    public static BlockPosAPI<?> getPos(double x, double y, double z) {
        return getPos(new Vector3((int)x,(int)y,(int)z));
    }

    public static BlockPosAPI<?> getPos(int x, int y, int z) {
        return getPos(new Vector3(x,y,z));
    }

}