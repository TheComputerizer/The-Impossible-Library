package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.v6.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.InfernalMobsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.SereneSeasonsAPI;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.integration.ModHelperNeoForge1_20;

import java.util.Collections;
import java.util.Map;

public class ModHelperNeoForge1_20_6 extends ModHelperNeoForge1_20 {

    public ModHelperNeoForge1_20_6(Side side) {
        super(true,side);
    }

    @Override protected Map<String,ModAPI> addSupportedMods(Map<String,ModAPI> map) {
        if(isModLoaded(InfernalMobsAPI.MODID)) addMod(map,new InfernalMobs1_20_6());
        if(isModLoaded(SereneSeasonsAPI.MODID)) addMod(map,new SereneSeasonsForge1_20_6());
        return Collections.unmodifiableMap(map);
    }
}