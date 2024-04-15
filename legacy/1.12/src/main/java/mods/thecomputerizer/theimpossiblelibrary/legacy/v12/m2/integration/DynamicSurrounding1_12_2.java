package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.integration.DynamicSurroundingsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.world.World;
import org.orecruncher.dsurround.client.weather.Weather;

public class DynamicSurrounding1_12_2 extends DynamicSurroundingsAPI {

    @Override
    public float getRainStrength(WorldAPI<?> api) {
        Object world = api.getWorld();
        return world instanceof World ? Weather.getIntensityLevel() : 0f;
    }
}