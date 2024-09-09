package mods.thecomputerizer.theimpossiblelibrary.api.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

public abstract class DynamicSurroundingsAPI implements ModAPI {

    public static final String MODID = "dsurround";
    public static final String NAME = "Dynamic Surroundings";

    protected DynamicSurroundingsAPI() {}

    @Override public String getID() {
        return MODID;
    }

    @Override public String getName() {
        return NAME;
    }
    
    @IndirectCallers public abstract float getRainStrength(WorldAPI<?> world);

    @Override public boolean isCompatible(ModLoader loader, Side side, GameVersion version) {
        if(!side.isClient()) return false;
        return loader.isLegacyForge() ? version.isV12() : loader.isModernForge() && version.isV16();
    }
}