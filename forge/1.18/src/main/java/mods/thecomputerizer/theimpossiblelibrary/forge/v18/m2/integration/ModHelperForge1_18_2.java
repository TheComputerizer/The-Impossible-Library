package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ChampionsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.EnhancedCelestialsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.GameStagesAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.SereneSeasonsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.Weather2API;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V18_2;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FORGE;

public class ModHelperForge1_18_2 extends ModHelperAPI {

    public ModHelperForge1_18_2(Side side) {
        super(V18_2,FORGE,side);
    }

    @Override protected Map<String,ModAPI> addSupportedMods(Map<String,ModAPI> map) {
        if(isModLoaded(ChampionsAPI.MODID)) addMod(map,new ChampionsForge1_18_2());
        if(isModLoaded(EnhancedCelestialsAPI.MODID)) addMod(map,new EnhancedCelestialsForge1_18_2());
        if(isModLoaded(GameStagesAPI.MODID)) addMod(map,new GameStagesForge1_18_2());
        if(isModLoaded(SereneSeasonsAPI.MODID)) addMod(map,new SereneSeasonsForge1_18_2());
        if(isModLoaded(Weather2API.MODID)) addMod(map,new Weather2Forge1_18_2());
        return Collections.unmodifiableMap(map);
    }
    
    @Override public String getModName(String modid) {
        String name = super.getModName(modid);
        ModList mods = ModList.get();
        if(Objects.nonNull(mods) && name.equals(modid) && mods.isLoaded(modid)) {
            for(IModInfo info : mods.getMods()) {
                if(modid.equals(info.getModId())) {
                    name = info.getDisplayName();
                    break;
                }
            }
        }
        return name;
    }

    @Override public boolean isModLoaded(String modid) {
        ModList mods = ModList.get();
        return Objects.nonNull(mods) && mods.isLoaded(modid);
    }
}