package mods.thecomputerizer.theimpossiblelibrary.api.world;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import org.joml.Vector3i;

import javax.annotation.Nullable;
import java.util.Objects;

public class PosHelper {

    public static @Nullable PosHelperAPI<?> getAPI() {
        return TILRef.getCommonSubAPI("PosHelperAPI",CommonAPI::getPosHelperAPI);
    }

    /**
     * Returns BlockPosAPI#ZERO if there is an issue getting the api
     */
    @SuppressWarnings("unchecked")
    public static <P> BlockPosAPI<?> getPos(P pos) {
        PosHelperAPI<?> api = getAPI();
        return Objects.nonNull(api) ? ((PosHelperAPI<P>)api).getPos(pos) : BlockPosAPI.ZERO;
    }

    /**
     * Returns BlockPosAPI#ZERO if there is an issue getting the api
     */
    public static BlockPosAPI<?> getPos(Vector3i vec) {
        PosHelperAPI<?> api = getAPI();
        return Objects.nonNull(api) ? api.getPos(vec) : BlockPosAPI.ZERO;
    }
}