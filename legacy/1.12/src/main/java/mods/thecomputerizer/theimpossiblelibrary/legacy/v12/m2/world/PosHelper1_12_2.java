package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world;

import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.PosHelperAPI;
import net.minecraft.util.math.BlockPos;
import org.joml.Vector3d;
import org.joml.Vector3i;

public class PosHelper1_12_2 implements PosHelperAPI<BlockPos> {

    @Override public BlockPosAPI<BlockPos> getPos(BlockPos pos) {
        return new BlockPos1_12_2(pos);
    }

    @Override public BlockPosAPI<BlockPos> getPos(Vector3i vec) {
        return new BlockPos1_12_2(vec);
    }

    @Override public BlockPosAPI<BlockPos> getPos(Vector3d vec) {
        return new BlockPos1_12_2(vec.x,vec.y,vec.z);
    }

    @Override public BlockPosAPI<BlockPos> getPos(double x, double y, double z) {
        return new BlockPos1_12_2(x,y,z);
    }

    @Override public BlockPosAPI<BlockPos> getPos(int x, int y, int z) {
        return new BlockPos1_12_2(x,y,z);
    }
}
