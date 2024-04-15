package mods.thecomputerizer.theimpossiblelibrary.api.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V12;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.LEGACY;

public abstract class BloodmoonAPI implements ModAPI {

    public static final String MODID = "bloodmoon";
    public static final String NAME = "Bloodmoon";

    protected BloodmoonAPI() {}

    @Override
    public String getID() {
        return MODID;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public abstract boolean isBloodMoon();

    @Override
    public boolean isCompatible(ModLoader loader, Side side, GameVersion version) {
        return version==V12 && loader==LEGACY;
    }
}