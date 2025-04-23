package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m2.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.integration.ModHelperFabric1_19;

import java.util.Collections;
import java.util.Map;

public class ModHelperFabric1_19_2 extends ModHelperFabric1_19 {

    public ModHelperFabric1_19_2(Side side) {
        super(true,side);
    }

    @Override protected Map<String,ModAPI> addSupportedMods(Map<String,ModAPI> map) {
        return Collections.unmodifiableMap(map);
    }
}
