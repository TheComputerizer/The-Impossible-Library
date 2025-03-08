package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m4.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.integration.ModHelperFabric1_20;

import java.util.Collections;
import java.util.Map;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V20_4;

public class ModHelperFabric1_20_4 extends ModHelperFabric1_20 {

    public ModHelperFabric1_20_4(Side side) {
        super(V20_4,side);
    }

    @Override protected Map<String,ModAPI> addSupportedMods(Map<String,ModAPI> map) {
        return Collections.unmodifiableMap(map);
    }
}
