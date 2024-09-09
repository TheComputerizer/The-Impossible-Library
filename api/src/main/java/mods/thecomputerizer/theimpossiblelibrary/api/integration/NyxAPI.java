package mods.thecomputerizer.theimpossiblelibrary.api.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

public abstract class NyxAPI implements ModAPI {

    public static final String MODID = "nyx";
    public static final String NAME = "Nyx";

    protected NyxAPI() {}

    @Override public String getID() {
        return MODID;
    }

    @Override public String getName() {
        return NAME;
    }
    
    @IndirectCallers public abstract boolean isBloodMoon(WorldAPI<?> world);

    @Override public boolean isCompatible(ModLoader loader, Side side, GameVersion version) {
        return version.isV12() && loader.isLegacyForge();
    }

    @IndirectCallers public abstract boolean isStarShower(WorldAPI<?> world);
    @IndirectCallers public abstract boolean isFullMoon(WorldAPI<?> world);
    @IndirectCallers public abstract boolean isHarvestMoon(WorldAPI<?> world);
}