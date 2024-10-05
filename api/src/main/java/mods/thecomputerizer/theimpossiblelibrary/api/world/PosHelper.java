package mods.thecomputerizer.theimpossiblelibrary.api.world;


import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import org.joml.Vector3d;
import org.joml.Vector3i;

public class PosHelper {

    public static BlockPosAPI<?> getPos(Object vec) {
        return WrapperHelper.wrapPosition(vec);
    }

    public static BlockPosAPI<?> getPos(Vector3d vec) {
        return getPos(new Vector3i((int)vec.x,(int)vec.y,(int)vec.z));
    }

    public static BlockPosAPI<?> getPos(double x, double y, double z) {
        return getPos(new Vector3i((int)x,(int)y,(int)z));
    }

    public static BlockPosAPI<?> getPos(int x, int y, int z) {
        return getPos(new Vector3i(x,y,z));
    }

}