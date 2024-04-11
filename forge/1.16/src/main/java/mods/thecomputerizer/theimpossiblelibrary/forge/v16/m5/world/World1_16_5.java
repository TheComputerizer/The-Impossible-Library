package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world;

import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldSettingsAPI;
import net.minecraft.world.IWorld;
import net.minecraft.world.WorldSettings;

public class World1_16_5 implements WorldAPI<IWorld> {

    private final IWorld world;

    public World1_16_5(IWorld world) {
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
