package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m1.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.EnhancedCelestialsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.GameStagesAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.InfernalMobsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.SereneSeasonsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.Weather2API;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.integration.ModHelperForge1_20;

import java.util.Collections;
import java.util.Map;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V20_1;

public class ModHelperForge1_20_1 extends ModHelperForge1_20 {

    public ModHelperForge1_20_1(Side side) {
        super(V20_1,side);
    }

    @Override protected Map<String,ModAPI> addSupportedMods(Map<String,ModAPI> map) {
        if(isModLoaded(EnhancedCelestialsAPI.MODID)) addMod(map,new EnhancedCelestialsForge1_20_1());
        if(isModLoaded(GameStagesAPI.MODID)) addMod(map,new GameStagesForge1_20_1());
        if(isModLoaded(InfernalMobsAPI.MODID)) addMod(map,new InfernalMobs1_20_1());
        if(isModLoaded(SereneSeasonsAPI.MODID)) addMod(map,new SereneSeasonsForge1_20_1());
        if(isModLoaded(Weather2API.MODID)) addMod(map,new Weather2Forge1_20_1());
        return Collections.unmodifiableMap(map);
    }
}