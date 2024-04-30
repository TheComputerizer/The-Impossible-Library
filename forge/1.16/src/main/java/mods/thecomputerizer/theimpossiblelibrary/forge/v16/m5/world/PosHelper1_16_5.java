package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world;

import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.PosHelperAPI;
import net.minecraft.util.math.BlockPos;
import org.joml.Vector3d;
import org.joml.Vector3i;

import javax.annotation.Nullable;

public class PosHelper1_16_5 implements PosHelperAPI<BlockPos> {

    @Override
    public @Nullable BlockPosAPI<BlockPos> getPos(BlockPos pos) {
        return new BlockPos1_16_5(pos);
    }

    @Override
    public @Nullable BlockPosAPI<BlockPos> getPos(Vector3i vec) {
        return new BlockPos1_16_5(vec);
    }

    @Override
    public BlockPosAPI<BlockPos> getPos(Vector3d vec) {
        return new BlockPos1_16_5(vec.x,vec.y,vec.z);
    }

    @Override
    public BlockPosAPI<BlockPos> getPos(double x, double y, double z) {
        return new BlockPos1_16_5(x,y,z);
    }

    @Override
    public BlockPosAPI<BlockPos> getPos(int x, int y, int z) {
        return new BlockPos1_16_5(x,y,z);
    }
}
