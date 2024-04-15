package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import net.minecraftforge.fml.common.Loader;

import java.util.Collections;
import java.util.Map;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V12;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.LEGACY;

public class ModHelper1_12_2 extends ModHelperAPI {

    public ModHelper1_12_2(Side side) {
        super(V12,LEGACY,side);
    }

    @Override
    protected Map<String,ModAPI> addSupportedMods(Map<String,ModAPI> map) {
        addMod(map,new Bloodmoon1_12_2());
        addMod(map,new Champions1_12_2());
        addMod(map,new DynamicSurrounding1_12_2());
        addMod(map,new GameStages1_12_2());
        addMod(map,new InfernalMobs1_12_2());
        addMod(map,new Nyx1_12_2());
        addMod(map,new SereneSeasons1_12_2());
        addMod(map,new Weather21_12_2());
        return Collections.unmodifiableMap(map);
    }

    @Override
    public boolean isModLoaded(String modid) {
        return Loader.isModLoaded(modid);
    }
}
