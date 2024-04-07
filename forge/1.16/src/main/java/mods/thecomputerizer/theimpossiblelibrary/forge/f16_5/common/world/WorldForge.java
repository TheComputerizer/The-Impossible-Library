package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.world;

import mods.thecomputerizer.theimpossiblelibrary.api.common.world.PosHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.WorldAPI;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class WorldForge implements WorldAPI<IWorld> {

    private final IWorld world;

    public WorldForge(IWorld world) {
        this.world = world;
    }

    @Override
    public IWorld getWorld() {
        return this.world;
    }

    @Override
    public boolean isClient() {
        return this.world.isClientSide();
    }

    @Override
    public boolean isServer() {
        return !this.world.isClientSide();
    }
}
