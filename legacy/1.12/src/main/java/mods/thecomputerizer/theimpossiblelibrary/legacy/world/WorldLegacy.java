package mods.thecomputerizer.theimpossiblelibrary.legacy.world;

import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldSettingsAPI;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;

public class WorldLegacy implements WorldAPI<World> {

    private final World world;

    public WorldLegacy(World world) {
        this.world = world;
    }

    @Override
    public WorldSettingsAPI<WorldSettings> getSettings() {
        return null;
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
