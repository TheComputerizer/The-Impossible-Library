package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.integration.DynamicSurroundingsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import org.orecruncher.lib.WorldUtils;

public class DynamicSurroundingsForge1_16_5 extends DynamicSurroundingsAPI {

    @Override public float getRainStrength(WorldAPI<?> api) {
        return WorldUtils.getRainStrength(api.unwrap(),0f);
    }
}