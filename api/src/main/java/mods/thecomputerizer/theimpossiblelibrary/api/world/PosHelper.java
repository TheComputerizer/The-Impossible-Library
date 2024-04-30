package mods.thecomputerizer.theimpossiblelibrary.api.world;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import org.joml.Vector3d;
import org.joml.Vector3i;

import javax.annotation.Nullable;
import java.util.Objects;

public class PosHelper {

    public static PosHelperAPI<?> getAPI() {
        return TILRef.getCommonSubAPI(CommonAPI::getPosHelper);
    }

    @SuppressWarnings("unchecked")
    public static <P> BlockPosAPI<?> getPos(P pos) {
        return ((PosHelperAPI<P>)getAPI()).getPos(pos);
    }

    public static BlockPosAPI<?> getPos(Vector3i vec) {
        return getAPI().getPos(vec);
    }

    public static BlockPosAPI<?> getPos(Vector3d vec) {
        return getAPI().getPos(vec);
    }

    public static BlockPosAPI<?> getPos(double x, double y, double z) {
        return getAPI().getPos(x,y,z);
    }

    public static BlockPosAPI<?> getPos(int x, int y, int z) {
        return getAPI().getPos(x,y,z);
    }

}