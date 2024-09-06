package mods.thecomputerizer.theimpossiblelibrary.api.world;

import org.joml.Vector3d;
import org.joml.Vector3i;

public interface PosHelperAPI<P> {

    BlockPosAPI<P> getPos(P pos);
    BlockPosAPI<P> getPos(Vector3i vec);
    BlockPosAPI<P> getPos(Vector3d vec);
    BlockPosAPI<P> getPos(double x, double y, double z);
    BlockPosAPI<P> getPos(int x, int y, int z);
}