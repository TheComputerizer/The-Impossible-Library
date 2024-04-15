package mods.thecomputerizer.theimpossiblelibrary.api.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FABRIC;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FORGE;

public abstract class EnhancedCelestialsAPI implements ModAPI {

    public static final String MODID = "enhancedcelestials";
    public static final String NAME = "Enhanced Celestials";

    protected EnhancedCelestialsAPI() {}

    @Override
    public String getID() {
        return MODID;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public abstract boolean isBloodMoon(WorldAPI<?> world);
    public abstract boolean isBlueMoon(WorldAPI<?> world);

    @Override
    public boolean isCompatible(ModLoader loader, Side side, GameVersion version) {
        switch(version) {
            case V16:
            case V18:
            case V19: return loader==FABRIC || loader==FORGE;
            case V20: return loader==FABRIC;
            default: return false;
        }
    }

    public abstract boolean isHarvestMoon(WorldAPI<?> world);
    public abstract boolean isMoon(WorldAPI<?> world);
}