package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.world;

import mods.thecomputerizer.theimpossiblelibrary.api.common.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.PosHelperAPI;
import net.minecraft.util.math.BlockPos;
import org.joml.Vector3i;

import javax.annotation.Nullable;

public class PosHelperForge implements PosHelperAPI<BlockPos> {

    @Override
    public @Nullable BlockPosAPI<BlockPos> getPos(BlockPos pos) {
        return new BlockPosForge(pos);
    }

    @Override
    public @Nullable BlockPosAPI<BlockPos> getPos(Vector3i vec) {
        return new BlockPosForge(vec);
    }
}
