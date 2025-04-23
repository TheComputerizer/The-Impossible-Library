package mods.thecomputerizer.theimpossiblelibrary.fabric.v21.m1.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.SereneSeasonsAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v21.integration.ModHelperFabric1_21;

import java.util.Collections;
import java.util.Map;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V21_1;

public class ModHelperFabric1_21_1 extends ModHelperFabric1_21 {

    public ModHelperFabric1_21_1(Side side) {
        super(V21_1,side);
    }

    @Override protected Map<String,ModAPI> addSupportedMods(Map<String,ModAPI> map) {
        if(isModLoaded(SereneSeasonsAPI.MODID)) addMod(map, new SereneSeasonsFabric1_21_1());
        return Collections.unmodifiableMap(map);
    }
}