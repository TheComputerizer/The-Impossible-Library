package mods.thecomputerizer.theimpossiblelibrary.api.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

public abstract class EnhancedCelestialsAPI implements ModAPI {

    public static final String MODID = "enhancedcelestials";
    public static final String NAME = "Enhanced Celestials";

    protected EnhancedCelestialsAPI() {}

    @Override public String getID() {
        return MODID;
    }

    @Override public String getName() {
        return NAME;
    }

    @IndirectCallers public abstract boolean isBloodMoon(WorldAPI<?> world);
    @IndirectCallers public abstract boolean isBlueMoon(WorldAPI<?> world);

    @Override public boolean isCompatible(ModLoader loader, Side side, GameVersion version) {
        return version.isCompatibleModernForge() && (loader.isFabric() || loader.isModernForge());
    }

    @IndirectCallers public abstract boolean isHarvestMoon(WorldAPI<?> world);
    @IndirectCallers public abstract boolean isMoon(WorldAPI<?> world);
}