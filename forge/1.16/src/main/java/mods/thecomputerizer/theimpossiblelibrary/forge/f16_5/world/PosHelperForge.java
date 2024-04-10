package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.world;

import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.PosHelperAPI;
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
