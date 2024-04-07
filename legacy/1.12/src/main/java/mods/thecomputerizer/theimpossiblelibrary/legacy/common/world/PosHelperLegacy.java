package mods.thecomputerizer.theimpossiblelibrary.legacy.common.world;

import mods.thecomputerizer.theimpossiblelibrary.api.common.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.PosHelperAPI;
import net.minecraft.util.math.BlockPos;
import org.joml.Vector3i;

import javax.annotation.Nullable;

public class PosHelperLegacy implements PosHelperAPI<BlockPos> {

    @Override
    public @Nullable BlockPosAPI<BlockPos> getPos(BlockPos pos) {
        return new BlockPosLegacy(pos);
    }

    @Override
    public @Nullable BlockPosAPI<BlockPos> getPos(Vector3i vec) {
        return new BlockPosLegacy(vec);
    }
}
