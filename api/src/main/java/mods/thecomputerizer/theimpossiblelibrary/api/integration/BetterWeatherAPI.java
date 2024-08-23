package mods.thecomputerizer.theimpossiblelibrary.api.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

@SuppressWarnings("unused")
public abstract class BetterWeatherAPI implements ModAPI {

    public static final String MODID = "betterweather";
    public static final String NAME = "Better Weather";

    protected BetterWeatherAPI() {}

    @Override
    public String getID() {
        return MODID;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public abstract boolean isAcidRaining(WorldAPI<?> world);
    public abstract boolean isBlizzard(WorldAPI<?> world);
    public abstract boolean isCloudy(WorldAPI<?> world);

    @Override
    public boolean isCompatible(ModLoader loader, Side side, GameVersion version) {
        return version.isV16() && loader.isModernForge() ;
    }

    public abstract boolean isRaining(WorldAPI<?> world);
}
