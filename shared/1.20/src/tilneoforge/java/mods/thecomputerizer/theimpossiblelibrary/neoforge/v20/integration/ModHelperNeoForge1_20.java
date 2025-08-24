package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V20_4;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V20_6;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.NEOFORGE;

public abstract class ModHelperNeoForge1_20 extends ModHelperAPI {

    protected ModHelperNeoForge1_20(boolean four, Side side) {
        super(four ? V20_4 : V20_6,NEOFORGE,side);
    }
}