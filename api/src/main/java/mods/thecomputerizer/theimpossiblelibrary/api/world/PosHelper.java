package mods.thecomputerizer.theimpossiblelibrary.api.world;

import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;

public class PosHelper {

    public static BlockPosAPI<?> getPos(Object vec) {
        return WrapperHelper.wrapPosition(vec);
    }

    public static BlockPosAPI<?> getPos(double x, double y, double z) {
        return getPos(new Vector3(x,y,z));
    }

    public static BlockPosAPI<?> getPos(int x, int y, int z) {
        return getPos(new Vector3(x,y,z));
    }
    
    /**
     * Rounds the input value to the nearest 0.5 (0 = 0.5, 0.75 = 0.5, 1 = 1.5, etc.)
     */
    public static double roundToCenter(double v) {
        int lower = (int)Math.floor(v*2d);
        if(Math.abs(lower%2)!=1) lower++; //Round up to an odd number if even
        return ((double)lower)/2d;
    }
}