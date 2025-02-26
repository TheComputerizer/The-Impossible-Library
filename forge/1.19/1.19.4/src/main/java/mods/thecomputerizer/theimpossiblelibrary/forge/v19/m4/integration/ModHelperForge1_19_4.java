package mods.thecomputerizer.theimpossiblelibrary.forge.v19.m4.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.EnhancedCelestialsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.GameStagesAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.InfernalMobsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.SereneSeasonsAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.integration.ModHelperForge1_19;

import java.util.Collections;
import java.util.Map;

public class ModHelperForge1_19_4 extends ModHelperForge1_19 {

    public ModHelperForge1_19_4(Side side) {
        super(false,side);
    }

    @Override protected Map<String,ModAPI> addSupportedMods(Map<String,ModAPI> map) {
        if(isModLoaded(EnhancedCelestialsAPI.MODID)) addMod(map,new EnhancedCelestialsForge1_19_4());
        if(isModLoaded(GameStagesAPI.MODID)) addMod(map,new GameStagesForge1_19_4());
        if(isModLoaded(InfernalMobsAPI.MODID)) addMod(map, new InfernalMobs1_19_4());
        if(isModLoaded(SereneSeasonsAPI.MODID)) addMod(map,new SereneSeasonsForge1_19_4());
        return Collections.unmodifiableMap(map);
    }
}