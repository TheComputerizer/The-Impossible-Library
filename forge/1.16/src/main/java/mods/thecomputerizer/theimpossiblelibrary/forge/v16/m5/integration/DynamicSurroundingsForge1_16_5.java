package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.integration.DynamicSurroundingsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.world.World;
import org.orecruncher.lib.WorldUtils;

public class DynamicSurroundingsForge1_16_5 extends DynamicSurroundingsAPI {

    @Override
    public float getRainStrength(WorldAPI<?> api) {
        Object world = api.getWorld();
        return world instanceof World ? WorldUtils.getRainStrength((World)world,0f) : 0f;
    }
}