package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.m1.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.InfernalMobsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.SereneSeasonsAPI;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.integration.ModHelperNeoForge1_21;

import java.util.Collections;
import java.util.Map;

public class ModHelperNeoForge1_21_1 extends ModHelperNeoForge1_21 {

    public ModHelperNeoForge1_21_1(Side side) {
        super(true,side);
    }

    @Override protected Map<String,ModAPI> addSupportedMods(Map<String,ModAPI> map) {
        if(isModLoaded(InfernalMobsAPI.MODID)) addMod(map,new InfernalMobs1_21_1());
        if(isModLoaded(SereneSeasonsAPI.MODID)) addMod(map,new SereneSeasonsForge1_21_1());
        return Collections.unmodifiableMap(map);
    }
}