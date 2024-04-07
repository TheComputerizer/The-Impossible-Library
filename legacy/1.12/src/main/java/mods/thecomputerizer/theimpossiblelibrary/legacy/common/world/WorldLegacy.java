package mods.thecomputerizer.theimpossiblelibrary.legacy.common.world;

import mods.thecomputerizer.theimpossiblelibrary.api.common.world.WorldAPI;
import net.minecraft.world.World;

public class WorldLegacy implements WorldAPI<World> {

    private final World world;

    public WorldLegacy(World world) {
        this.world = world;
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    @Override
    public boolean isClient() {
        return this.world.isRemote;
    }

    @Override
    public boolean isServer() {
        return !this.world.isRemote;
    }
}
