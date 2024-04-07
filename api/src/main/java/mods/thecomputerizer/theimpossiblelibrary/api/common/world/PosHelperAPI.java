package mods.thecomputerizer.theimpossiblelibrary.api.common.world;

import org.joml.Vector3i;

import javax.annotation.Nullable;

public interface PosHelperAPI<P> {

    @Nullable BlockPosAPI<P> getPos(P pos);
    @Nullable BlockPosAPI<P> getPos(Vector3i vec);
}