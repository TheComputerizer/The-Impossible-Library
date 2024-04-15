package mods.thecomputerizer.theimpossiblelibrary.api.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.*;

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
        switch(version) {
            case V12: return loader==LEGACY;
            case V16:
            case V18:
            case V19: return loader==FORGE;
            case V20: return loader==FABRIC || loader==FORGE || loader==NEOFORGE;
            default: return false;
        }
    }

    public abstract boolean isSpring(WorldAPI<?> world);
    public abstract boolean isSummer(WorldAPI<?> world);
    public abstract boolean isWinter(WorldAPI<?> world);
}