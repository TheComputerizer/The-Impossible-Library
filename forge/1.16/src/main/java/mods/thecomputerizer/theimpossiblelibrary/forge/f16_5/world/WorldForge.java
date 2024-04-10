package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.world;

import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldSettingsAPI;
import net.minecraft.world.IWorld;
import net.minecraft.world.WorldSettings;

public class WorldForge implements WorldAPI<IWorld> {

    private final IWorld world;

    public WorldForge(IWorld world) {
        this.world = world;
    }

    @Override
    public WorldSettingsAPI<WorldSettings> getSettings() {
        return null;
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
