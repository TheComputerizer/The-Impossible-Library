package mods.thecomputerizer.theimpossiblelibrary.api.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;

@SuppressWarnings("unused")
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
        return version.isV12() && loader.isLegacyForge();
    }
}