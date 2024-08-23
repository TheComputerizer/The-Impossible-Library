package mods.thecomputerizer.theimpossiblelibrary.api.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V20_1;

@SuppressWarnings("unused")
public abstract class SereneSeasonsAPI implements ModAPI {

    public static final String MODID = "sereneseasons";
    public static final String NAME = "Serene Seasons";

    protected SereneSeasonsAPI() {}

    @Override
    public String getID() {
        return MODID;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public abstract boolean isAutumn(WorldAPI<?> world);

    @Override
    public boolean isCompatible(ModLoader loader, Side side, GameVersion version) {
        return loader.isForge() || version.isV21() || (version.isV20() && version!=V20_1);
    }

    public abstract boolean isSpring(WorldAPI<?> world);
    public abstract boolean isSummer(WorldAPI<?> world);
    public abstract boolean isWinter(WorldAPI<?> world);
}